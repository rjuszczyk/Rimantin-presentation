package pl.pharmaway.rimantin_presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AngleFrameLayout extends FrameLayout {
    public AngleFrameLayout(Context context) {
        super(context);
    }

    public AngleFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AngleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AngleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAngle(float a) {

//        float alpha1;
//        float alpha2;
//        if(a<0) {
//            alpha1 = -a;
//            alpha2 = 0;
//        } else {
//            alpha1 = 0;
//            alpha2 = a;
//        }
//
//        getChildAt(1).setAlpha(alpha1);
//        getChildAt(2).setAlpha(alpha2);
    }
}
