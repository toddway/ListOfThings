package com.vml.listofthings.app.thinglist;

import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by tway on 7/28/16.
 */
public class PinchSpreadHelper {

    ScaleGestureDetector detector;
    long previousEventEndTime;

    public PinchSpreadHelper(Listener listener, View attachedView) {
        this.detector = new ScaleGestureDetector(
                attachedView.getContext(),
                new ScaleGestureDetector.OnScaleGestureListener() {
                    public boolean hasScaled = false;

                    @Override
                    public boolean onScaleBegin(ScaleGestureDetector detector) {
                        hasScaled = false;
                        return true;
                    }

                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        if (!hasScaled) {
                            if (detector.getScaleFactor() > 1.1) {
                                listener.onSpread();
                                hasScaled = true;
                            } else if (detector.getScaleFactor() < .9) {
                                listener.onPinch();
                                hasScaled = true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onScaleEnd(ScaleGestureDetector detector) {
                        previousEventEndTime = detector.getEventTime();
                    }
                }
        );

        attachedView.setOnTouchListener((v, event) -> {
            detector.onTouchEvent(event);
            return isFinishedScaling();
        });
    }

    private boolean isFinishedScaling() {
        return detector.isInProgress() || (previousEventEndTime + 100) > detector.getEventTime();
    }

    interface Listener {
        void onPinch();
        void onSpread();
    }
}
