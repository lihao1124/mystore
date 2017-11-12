package com.wifiyou.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;


/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         4/27/16
 */
public class ViewUtils {
    /**
     * Sets the background of view.
     *
     * @param view       view
     * @param background background drawable
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }


    /**
     * Creates a view.
     *
     * @param parent parent view
     * @param resId  resource id
     * @return view
     */
    public static View newInstance(ViewGroup parent, int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    /**
     * Creates a view.
     *
     * @param context context
     * @param resId   resource id
     * @return view
     */
    public static View newInstance(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, null);
    }

    public static Activity findActivityByView(View view) {
        if (view == null || view.getContext() == null) {
            return null;
        }
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static void setHeightForWrapContent(View content, View view) {
        int contentWidth = content.getMeasuredWidth();
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(contentWidth, View.MeasureSpec.EXACTLY);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        int height = view.getMeasuredHeight();
        view.getLayoutParams().height = height;
    }

    public static void expand(final View view) {
        if (view == null) {
            return;
        }
        if (view.getMeasuredHeight() == 0) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    expandView(view);
                    view.removeOnLayoutChangeListener(this);
                }
            });
        } else {
            expandView(view);
        }
    }

    private static void expandView(final View view) {
        final int measureHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = (int) (measureHeight * interpolatedTime);
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(500);
        view.startAnimation(a);
    }
}
