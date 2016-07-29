package com.vml.listofthings.app.thingdetail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by tway on 7/15/16.
 */
public class RevealUtil {

    private static int DURATION = 500;

    public static void circleReveal(final View targetView, View anchorView, boolean isReverse) {
        circleReveal(targetView, getCenterOf(anchorView), isReverse);
    }

    public static void circleReveal(final View targetView, int[] centerpoint, boolean isReverse) {
        circleReveal(targetView, centerpoint[0], centerpoint[1], isReverse);
    }

    public static void circleReveal(final View targetView, int centerX, int centerY, boolean isReverse) {
        if (targetView == null || !targetView.isAttachedToWindow()) return;
        int radius = (int) Math.hypot(targetView.getHeight(), targetView.getWidth());

        Animator animator;
        if (isReverse) {
            animator = isCircleRevealSupported()
                    ? ViewAnimationUtils.createCircularReveal(targetView, centerX, centerY, radius, 0)
                    : createFade(targetView, 1, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    targetView.setVisibility(View.GONE);
                }
            });
        } else {
            animator = isCircleRevealSupported()
                    ? ViewAnimationUtils.createCircularReveal(targetView, centerX, centerY, 0, radius)
                    : createFade(targetView, 0, 1);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    targetView.setVisibility(View.VISIBLE);
                }
            });
        }
        animator.setDuration(DURATION);
        animator.start();
    }

    private static Animator createFade(final View targetView, int startAlpha, int endAlpha) {
        ValueAnimator a = ValueAnimator.ofFloat(startAlpha, endAlpha);
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setAlpha((float) valueAnimator.getAnimatedValue());
            }
        });
        return a;
    }

    private static boolean isCircleRevealSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private static int[] getCenterOf(View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        loc[0] += (view.getWidth() / 2); //middle of button
        loc[1] += (view.getHeight() / 2); //middle of button
        return loc;
    }
}
