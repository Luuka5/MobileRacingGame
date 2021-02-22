package com.example.a2drace;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends Activity {

    private static InterstitialAd ad;
    private static RewardedVideoAd adR;
    private static boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Game(this));


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        ad = new InterstitialAd(this);
        ad.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        ad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                ad.loadAd(new AdRequest.Builder().build());
            }
        });

        ad.loadAd(new AdRequest.Builder().build());

        adR = MobileAds.getRewardedVideoAdInstance(this);
        adR.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });
        loadRewardedVideoAd();
        ready = true;

    }


    static void loadRewardedVideoAd() {
        adR.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
        adR.show();
    }

    static void showRewardedAd() {
        if (!ready) return;

        if (MainActivity.adR.isLoaded()) {
            MainActivity.adR.show();
        } else {
            System.out.println("Ad not loaded");
        }
    }

    static void showAd() {
        if (!ready) return;

        if (MainActivity.ad.isLoaded()) {
            MainActivity.ad.show();
        } else {
            System.out.println("Ad not loaded");
        }
    }

}
