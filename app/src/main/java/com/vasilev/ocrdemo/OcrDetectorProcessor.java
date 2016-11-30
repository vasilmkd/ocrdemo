package com.vasilev.ocrdemo;


import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {
    private static final String LOG_TAG = OcrDetectorProcessor.class.getSimpleName();

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }

    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        final SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); i++) {
            final TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                Log.d(LOG_TAG, "Text detected! " + item.getValue());
            }
            final OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
        }
    }
}
