package com.zhanghao.pocketweather.UI.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 自定义趋势折线图
 * Created by Administrator on 2016/5/15.
 */
public class TreadView  extends View{
    //传进来的高度和宽度
    private float Width;
    private float Height;
    //趋势图实际的宽度和高度
    private float trendWidth;
    private float trendHeight;

    //最高温度线绘制类，
    private Paint maxLinePaint;
    //最低温度线绘制类
    private Paint minLinePaint;
    //点绘制
    private Paint mPointPaint;
    //温度值文字绘制类
    private TextPaint textPaint;

    //最高温度值
    private ArrayList<Integer> maxTemps;
    //最低温度值
    private ArrayList<Integer> minTemps;

    //横向各温度点的x坐标,因为目前是五个点，所以数组长度目前设置为5,如果温度点不止五个，以后可以重新定义
    private float x[]=new float[5];

    public TreadView(Context context) {
        super(context);
    }

    public TreadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    //初始化
    public void init(){
        //文字绘制
        textPaint=new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25f);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //最高气温线绘制
        maxLinePaint=new Paint();
        maxLinePaint.setColor(Color.YELLOW);
        maxLinePaint.setStrokeWidth(4f);
        maxLinePaint.setStyle(Paint.Style.FILL);

        //最低气温度线
        minLinePaint=new Paint();
        minLinePaint.setColor(Color.RED);
        minLinePaint.setStrokeWidth(4f);
        minLinePaint.setStyle(Paint.Style.FILL);

        //气温点绘制
        mPointPaint=new Paint();
        mPointPaint.setColor(Color.WHITE);
        mPointPaint.setAntiAlias(true);
    }

    /**
     * 传入温度值
     */
    public void setTemperature(ArrayList<Integer> maxTemps,ArrayList<Integer> minTemps){
        this.maxTemps=maxTemps;
        this.minTemps=minTemps;
        postInvalidate();
    }
    //如果控件本身没有设置具体的宽高值，必须传入宽高
    public void setSize(float mWidth,float mHeight){
        this.Width=mWidth;
        this.Height=mHeight;
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
        trendWidth=measureWidth(widthMeasureSpec);
        trendHeight=measureHeight(heightMeasureSpec);
        //获得各温度点的横坐标值
        x[0]=trendWidth/10.0f;
        x[1]=trendWidth*3.0f/10.0f;
        x[2]=trendWidth*5.0f/10.0f;
        x[3]=trendWidth*7.0f/10.0f;
        x[4]=trendWidth*9.0f/10.0f;

    }
    //测量宽度
    private int measureWidth(int widthMeasureSpec){
        int result=0;
        int specMode=MeasureSpec.getMode(widthMeasureSpec);
        int specSize=MeasureSpec.getSize(widthMeasureSpec);
        if (specMode==MeasureSpec.EXACTLY){
            result=specSize;

        }
        else{
            result=getPaddingLeft()+getPaddingRight()+(int)Width;
            if (specMode==MeasureSpec.AT_MOST){
                result=Math.min(result,specSize);
            }
        }
        return  result;
    }

   //测量高度
    private int measureHeight(int heightMeasureSpec){
        int result=0;
        int specMode=MeasureSpec.getMode(heightMeasureSpec);
        int specSize=MeasureSpec.getSize(heightMeasureSpec);
        if (specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }
        else{
            result=getPaddingBottom()+getPaddingTop()+(int)Height;
            if (specMode==MeasureSpec.AT_MOST){
                result=Math.min(result,specSize);
            }
        }
        return  result;
    }
    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mutiple=12; //设置温度偏移量为8，根据屏幕分辨率设置

        //以控件的中央为0摄氏度
        float h=trendHeight/2.0f;
        //计算文字的高度
        Paint.FontMetrics  fontMetrics=textPaint.getFontMetrics();
        float fontHeight=fontMetrics.bottom-fontMetrics.top;

        //最高温度线的文字位置
        float h1=h-fontHeight/2;
        //最低温度线的文字位置
        float h2=h+fontHeight;
        //最高温度线的绘制
        for (int i=0;i<maxTemps.size();i++){
            float point=-maxTemps.get(i)*mutiple;
            if (i!=(maxTemps.size()-1)){
                float pointNext=-maxTemps.get(i+1)*mutiple;
                canvas.drawLine(x[i],point+h,x[i+1],pointNext+h,maxLinePaint);
            }
            canvas.drawCircle(x[i],point+h,8f,mPointPaint);
            canvas.drawText(maxTemps.get(i)+"°",x[i],point+h1,textPaint);
        }
        //最低温度线的绘制
        for (int i=0;i<minTemps.size();i++){
            float point=-minTemps.get(i)*mutiple;
            if (i!=(minTemps.size()-1)){
                float pointNext=-minTemps.get(i+1)*mutiple;
                canvas.drawLine(x[i],point+h,x[i+1],pointNext+h,minLinePaint);
            }
            canvas.drawCircle(x[i],point+h,8f,mPointPaint);
            canvas.drawText(minTemps.get(i)+"°",x[i],point+h2,textPaint);
        }

    }
}
