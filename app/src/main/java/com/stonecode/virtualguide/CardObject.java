package com.stonecode.virtualguide;

import android.widget.Button;

/**
 * Created by dhruv on 29/12/16.
 */

public class CardObject {
    private int mDrawableImage;
    private String mService;
    private Button buttonCard;
    CardObject(int drawableImage,String service)
    {
        mDrawableImage = drawableImage;
        mService = service;
    }

    public int getmDrawableImage() {
        return mDrawableImage;
    }

    public void setmDrawableImage(int mDrawableImage) {
        this.mDrawableImage = mDrawableImage;
    }

    public String getmService() {
        return mService;
    }

    public void setmService(String mService) {
        this.mService = mService;
    }

    public Button getButtonCard() {
        return buttonCard;
    }

    public void setButtonCard(Button buttonCard) {
        this.buttonCard = buttonCard;
    }
}
