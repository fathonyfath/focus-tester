package com.papbg.focustester.fiturColorClick;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.papbg.focustester.R;
import com.papbg.focustester.utils.BaseFragment;

import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/27/2016.
 */

public class ColorClickFragment extends BaseFragment {

    @BindView(R.id.colorClick_background)
    RelativeLayout background;

    @BindView(R.id.colorClick_notification)
    TextView notification;

    @BindView(R.id.colorClick_container)
    LinearLayout container;
    @BindView(R.id.colorClick_instruction)
    TextView instruction;
    @BindView(R.id.colorClick_startButton)
    Button startButton;

    @BindView(R.id.colorClick_timer)
    TextView timer;

    private Random random;

    private float minStart = 1.0f;
    private float maxStart = 7.0f;

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    private int second;
    private int millisecond;

    private boolean gameIsStarting;

    private Runnable stopwatchRunnable;
    private ScreenChangerRunnable screenChangerRunnable;
    private Handler handler;

    private final int[] BACKGROUND_POSSIBILITIES =
            {R.color.material_amber_200, R.color.material_blue_200, R.color.material_blue_grey_200,
            R.color.material_brown_200, R.color.material_cyan_200, R.color.material_deep_purple_200,
            R.color.material_green_200};

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        random = new Random();

        handler = new Handler();

        background.setOnTouchListener(null);

        reset();
        hideNotification();

        stopwatchRunnable = new Runnable() {
            @Override
            public void run() {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                second = (int) (updatedTime / 1000);
                second = second % 60;
                millisecond = (int) (updatedTime % 1000);

                String text = String.format(Locale.US, "%d:%03d", second, millisecond);
                timer.setText(text);

                handler.postDelayed(this, 0);
            }
        };
    }

    private void showNotification(@StringRes int stringRes) {
        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setFillAfter(true);
        notification.startAnimation(animation);
        notification.setVisibility(View.VISIBLE);
        notification.setText(stringRes);
    }

    private void hideNotification() {
        notification.setVisibility(View.GONE);
        notification.setText("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_color_click;
    }

    private void reset() {
        gameIsStarting = false;
        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setFillAfter(true);
        container.startAnimation(animation);
        container.setVisibility(View.VISIBLE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background.setBackgroundColor(Color.WHITE);
                new Thread(new AnimationRunnable()).start();
            }
        });

        background.setOnTouchListener(null);

        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
    }

    private void startGame() {
        double randomStartTime = random.nextFloat() * (maxStart - minStart) + minStart;
        long startTime = (long) (randomStartTime * 1000);

        screenChangerRunnable = new ScreenChangerRunnable(startTime);

        new Thread(screenChangerRunnable).start();

        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    handler.removeCallbacks(stopwatchRunnable);
                    if(gameIsStarting) {
                        long milliDelay = (second * 1000) + millisecond;
                        checkWinningCondition(milliDelay);
                        reset();
                    } else {
                        screenChangerRunnable.stop();
                        showNotification(R.string.colorClick_gameNotStarting);
                        reset();
                    }
                }
                return true;
            }
        });
    }

    private void startTime() {
        startTime = SystemClock.uptimeMillis();
        handler.removeCallbacks(stopwatchRunnable);
        handler.postDelayed(stopwatchRunnable, 0);
    }

    private void checkWinningCondition(long millisecond) {
        if(millisecond < 500) showNotification(R.string.colorClick_win);
        else showNotification(R.string.colorClick_lose);
    }

    private class AnimationRunnable implements Runnable {

        @Override
        public void run() {
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Animation animation = new AlphaAnimation(1f, 0f);
                        animation.setFillAfter(true);
                        animation.setDuration(1000);
                        container.startAnimation(animation);

                        Animation textAnimation = new AlphaAnimation(1f, 0f);
                        textAnimation.setFillAfter(true);
                        textAnimation.setDuration(1000);
                        notification.startAnimation(textAnimation);
                    }
                });
                Thread.sleep(1000);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        container.clearAnimation();
                        notification.clearAnimation();
                        container.setVisibility(View.GONE);
                        notification.setVisibility(View.GONE);
                        startButton.setOnClickListener(null);
                        startGame();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ScreenChangerRunnable implements Runnable {

        private long startTime;
        private volatile boolean needContinue;

        public ScreenChangerRunnable(long startTime) {
            this.startTime = startTime;
            needContinue = true;
        }

        public void stop() {
            needContinue = false;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(startTime);
                if(needContinue) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            gameIsStarting = true;
                            int randomIndex = random.nextInt(BACKGROUND_POSSIBILITIES.length);
                            background.setBackgroundResource(BACKGROUND_POSSIBILITIES[randomIndex]);
                        }
                    });
                    startTime();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
