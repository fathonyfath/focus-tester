package com.papbg.focustester.fiturFollowMe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.papbg.focustester.R;
import com.papbg.focustester.utils.BaseFragment;

public class FollowMeFragment extends BaseFragment {


    private RelativeLayout layout;
    private ImageView image[] = new ImageView[4];

    private Thread thread[] = new Thread[4];
    private Thread threadWait;
    private Thread threadStop;

    private int imageSelect;

    private BallRunnable runnable[] = new BallRunnable[4];

    private View.OnClickListener correctButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getParentActivity(), "Pilihan kamu benar!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getParentActivity(), "Keluar kemudian masuk lagi ke halaman ini untuk restart.", Toast.LENGTH_SHORT).show();
            for(int i = 0; i < image.length; i++) {
                image[i].setOnClickListener(null);
            }
        }
    };
    private View.OnClickListener wrongButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getParentActivity(), "Pilihan kamu salah.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getParentActivity(), "Keluar kemudian masuk lagi ke halaman ini untuk restart.", Toast.LENGTH_SHORT).show();
            for(int i = 0; i < image.length; i++) {
                image[i].setOnClickListener(null);
            }
        }
    };


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = (RelativeLayout) view.findViewById(R.id.container);

        image[0] = (ImageView) view.findViewById(R.id.image);
        image[1] = (ImageView) view.findViewById(R.id.image2);
        image[2] = (ImageView) view.findViewById(R.id.image3);
        image[3] = (ImageView) view.findViewById(R.id.image4);

        imageSelect = (int) (Math.random() * 4);

        threadStop = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = (int) (Math.random() * 3000 + 2000);
                try {
                    Thread.sleep(time);
                    runnable[0].stop();
                    runnable[1].stop();
                    runnable[2].stop();
                    runnable[3].stop();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0; i < image.length; i++) {
                                image[i].setOnClickListener(wrongButton);
                            }
                            image[imageSelect].setOnClickListener(correctButton);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadWait = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            runnable[imageSelect].image.setBackgroundResource(R.color.colorPrimary);
                        }
                    });
                    Thread.sleep(1500);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            runnable[imageSelect].image.setBackgroundResource(R.color.colorAccent);
                        }
                    });

                    Thread.sleep(1000);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < runnable.length; i++) {
                                image[i].post(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int j = 0; j < runnable.length; j++) {
                                            onImageReady(image[j], runnable[j], thread[j]);
                                        }
                                    }
                                });
                            }

                            layout.post(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < runnable.length; i++) {
                                        onContainerReady(runnable[i], thread[i]);
                                    }
                                }
                            });

                            threadStop.start();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        for (int i = 0; i < runnable.length; i++) {
            runnable[i] = new BallRunnable(image[i], (float) (Math.random() * 11 + 20));
            thread[i] = new Thread(runnable[i]);
        }

        threadWait.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow_me;
    }


    private void onContainerReady(BallRunnable runnable, Thread thread) {
        runnable.setBorders(layout.getMeasuredWidth(), layout.getMeasuredHeight());
        if (runnable.isRunnableReady()) thread.start();
    }

    private void onImageReady(ImageView image, BallRunnable runnable, Thread thread) {
        runnable.setImageBorder(image.getMeasuredWidth(), image.getMeasuredHeight());
        if (runnable.isRunnableReady()) thread.start();
    }

    private static class BallRunnable implements Runnable {

        private ImageView image;
        private float borderX, borderY;
        private float imageBorderX, imageBorderY;

        private boolean isBoundReady;
        private boolean isImageBorderReady;

        private boolean isRunning;

        private float speedX, speedY;
        private float speed;

        private Handler handler;

        public BallRunnable(ImageView image, float speed) {
            this.image = image;
            isRunning = true;

            speedX = 1f;
            speedY = 1f;

            this.speed = speed;
            handler = new Handler(Looper.getMainLooper());
        }

        public void setBorders(float borderX, float borderY) {
            this.borderX = borderX;
            this.borderY = borderY;
            isBoundReady = true;
        }

        public void setImageBorder(float imageBorderX, float imageBorderY) {
            this.imageBorderX = imageBorderX;
            this.imageBorderY = imageBorderY;
            isImageBorderReady = true;
        }

        public boolean isRunnableReady() {
            return isBoundReady && isImageBorderReady;
        }

        public void stop() {
            isRunning = false;
        }

        public void start() {
            isRunning = true;
        }

        @Override
        public void run() {
            while (isRunning) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (image.getX() < 0f) {
                            image.setX(0f);
                            speedX *= -1f;
                        }

                        if (image.getX() > borderX - imageBorderX) {
                            image.setX(borderX - imageBorderX);
                            speedX *= -1f;
                        }

                        if (image.getY() < 0) {
                            image.setY(0f);
                            speedY *= -1f;
                        }

                        if (image.getY() > borderY - imageBorderY) {
                            image.setY(borderY - imageBorderY);
                            speedY *= -1f;
                        }

                        image.setX(image.getX() + speed * speedX);
                        image.setY(image.getY() + speed * speedY);
                    }
                });
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
