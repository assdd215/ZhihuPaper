package com.example.aria.baike.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.aria.baike.R;

/**
 * Created by Aria on 2017/2/18.
 */

public class ClearEditText extends EditText implements View.OnFocusChangeListener,TextWatcher{

    private Drawable ClearDrawable;
    private boolean hasFocus;

    public ClearEditText(Context context){
        this(context,null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs,R.attr.editTextStyle);
    }

    public ClearEditText(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        init();
    }

    private void init(){
        //获取EditText的DrawableRight,
        // 假如没有设置我们就使用默认的图片,
        // getCompoundDrawables()获取Drawable的四个位置的数组
        ClearDrawable = getCompoundDrawables()[2];
        if (ClearDrawable == null){
            Log.d("MainActivity","ClearDrawable is null");
                ClearDrawable = getResources().getDrawable(R.drawable.ic_clear_black_18dp);

        }
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的大小
        ClearDrawable.setBounds(0,0,ClearDrawable.getIntrinsicWidth(),ClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setCLearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null){
            //getTotalPaddingRight()图标左边缘至控件右边缘的距离
            //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
            //getWidth() - getPaddingRight() 表示最左边到图标右边缘的位置
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) &&
                                (event.getX() < (getWidth() - getPaddingRight()));
            if (touchable){
                this.setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus){
            setCLearIconVisible(getText().length()>0);
        }else {
            setCLearIconVisible(false);
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFocus){
            setCLearIconVisible(text.length()>0);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setCLearIconVisible(boolean visible){
        Drawable right = visible ? ClearDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
        this.startAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
