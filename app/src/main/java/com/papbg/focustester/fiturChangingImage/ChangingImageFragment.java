package com.papbg.focustester.fiturChangingImage;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.papbg.focustester.R;
import com.papbg.focustester.utils.BaseFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChangingImageFragment extends BaseFragment {

    private List<Integer> imageDataResources;
    private ImageAdapter adapter;
    private Random random;

    private int randomSeconds;

    GridView gridview;
    ProgressBar progressBar;
    Button restartButton;

    CountDownTimer waitTimer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        random = new Random();

        Integer[] mThumbIds = {
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
        };

        imageDataResources = Arrays.asList(mThumbIds);

        adapter = new ImageAdapter(getParentActivity(), imageDataResources);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        restartButton = (Button) view.findViewById(R.id.button);

        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        randomSeconds = random.nextInt(4) + 3;

        waitTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                getParentActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progressBar.getProgress() + 1);
                        if (progressBar.getProgress() == randomSeconds) {
                            changeImage();
                        }
                    }
                });
            }

            public void onFinish() {
                progressBar.setProgress(10);
                Toast.makeText(getParentActivity(), "Maaf Anda Kalah", Toast.LENGTH_SHORT).show();
                gridview.setOnItemClickListener(null);
            }
        }.start();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Integer clickedItem = imageDataResources.get(position);
                if (clickedItem == R.drawable.panda_android_changes) {
                    Toast.makeText(getParentActivity(), "Anda Benaaar", Toast.LENGTH_SHORT).show();
                    if (waitTimer != null) {
                        waitTimer.cancel();
                        waitTimer = null;
                        gridview.setOnItemClickListener(null);
                    }
                } else {
                    Toast.makeText(getParentActivity(), "Anda Salaaah", Toast.LENGTH_SHORT).show();
                    gridview.setOnItemClickListener(null);
                    if (waitTimer != null) {
                        waitTimer.cancel();
                        waitTimer = null;
                        gridview.setOnItemClickListener(null);
                    }
                }
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressBar.setProgress(0);
                restartGame();
            }
        });
    }

    private void changeImage() {
        int indexAcak = random.nextInt(imageDataResources.size() - 1);
        imageDataResources.set(indexAcak, R.drawable.panda_android_changes);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_changing_image;
    }

    private void restartGame() {
        if (waitTimer != null) {
            waitTimer.cancel();
            waitTimer = null;
            gridview.setOnItemClickListener(null);
        }

        random = new Random();

        Integer[] mThumbIds = {
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
                R.drawable.panda_android, R.drawable.panda_android,
        };

        imageDataResources = Arrays.asList(mThumbIds);

        adapter = new ImageAdapter(getParentActivity(), imageDataResources);

        gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        randomSeconds = random.nextInt(4) + 3;

        waitTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                getParentActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progressBar.getProgress() + 1);
                        if (progressBar.getProgress() == randomSeconds) {
                            changeImage();
                        }
                    }
                });
            }

            public void onFinish() {
                progressBar.setProgress(10);
                Toast.makeText(getParentActivity(), "Maaf Anda Kalah", Toast.LENGTH_SHORT).show();
                gridview.setOnItemClickListener(null);
            }
        }.start();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Integer clickedItem = imageDataResources.get(position);
                if (clickedItem == R.drawable.panda_android_changes) {
                    Toast.makeText(getParentActivity(), "Anda Benaaar", Toast.LENGTH_SHORT).show();
                    if (waitTimer != null) {
                        waitTimer.cancel();
                        waitTimer = null;
                        gridview.setOnItemClickListener(null);
                    }
                } else {
                    Toast.makeText(getParentActivity(), "Anda Salaaah", Toast.LENGTH_SHORT).show();
                    gridview.setOnItemClickListener(null);

                    if (waitTimer != null) {
                        waitTimer.cancel();
                        waitTimer = null;
                        gridview.setOnItemClickListener(null);
                    }

                }
            }
        });
    }
}
