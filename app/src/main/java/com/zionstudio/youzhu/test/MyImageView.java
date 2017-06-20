package com.zionstudio.youzhu.test;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.zionstudio.youzhu.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hasee on 2/17/2017.
 */
/**
 * 实现一个能够
 *    完整 正确
 * 显示图片的 自定义 ImageView控件
 */
public class MyImageView extends View {
	//画笔
	private Paint mBitmapPaint;
	//图片Drawable
	private Drawable mDrawable;
	//View 的高度 宽度
	private int mWidth;
	private int mHeight;


	public MyImageView(Context context) {
		this(context, null);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context,attrs);
		initAttrs(attrs);
		//初始化画笔
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);
	}

	private void initAttrs(AttributeSet attrs) {

		if(attrs != null){
			//ImageView的属性集
			TypedArray array = null;
			try {
				//首先读取MyImageView的属性集TypedArray，再从该对象中读取 MyImageView_src 的属性值
				array = getContext().obtainStyledAttributes(attrs, R.styleable.MyImageView);
				//根据id值从TypedArray对象中获取到该id对应的Drawable
				mDrawable = array.getDrawable(R.styleable.MyImageView_src);
				//测量Drawable对象的宽、高
				measureDrawable();
			} finally {
				if(array != null){
					array.recycle();
				}
			}
		}

	}

	/**
	 * 设置View的宽和高为图片的宽和高
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(mWidth, mHeight);
	}

	/**
	 * 根据Drawable绘制图片
	 * @param canvas
	 */
//	@Override
//	protected void onDraw(Canvas canvas) {
//		if(mDrawable == null){
//			return;
//		}
//
//		canvas.drawBitmap(drawableToBitmap(mDrawable),
//				getLeft(),
//				getTop(),
//				mBitmapPaint);
//	}

	/**
	 *  获取Drawable对象的宽和高并保存
	 */
	private void measureDrawable() {

		if(mDrawable == null){
			throw new RuntimeException("drawable 不能为空");
		}
		mWidth = mDrawable.getIntrinsicWidth();
		mHeight = mDrawable.getIntrinsicHeight();
	}

	/**
	 *  抛出变量值为空异常
	 *  所有标准异常类都有两个构造器
	 *  1、一个是默认构造器
	 *  2、一个是接受字符串作为参数，以便能把相关信息放入异常对象的构造器
	 */
	public void NullException(){

		String t = new String();
		if(t == null) {
			throw new NullPointerException();
		}
		throw new NullPointerException("t != null");


	}

	private void doEveryThing() {

	}

	/**
	 *  Throwable类型可以抛出任意类型的异常，他是异常类型的根类
	 *  对于不同类型的异常，要抛出相应的异常
	 *  错误信息可以保存在异常对象内部或者用异常类的名称来暗示
	 *  上一层环境通过这些信息来决定如何处理异常
	 *  通常，异常对象中仅有的信息就是异常类型，除此之外不包含任何有意义的内容
	 */
	class MyThrow extends Throwable{

		private MyThrow() {
			super();
		}
	}

	/**
	 * 异常捕获，监控区域（guarded region）
	 * 一段可能产生异常的代码
	 */
	class MyException extends Exception {}

	public class InheritingException {

		public void f() throws MyException{
			System.out.print("Throw MyException from f()");
//			System.err("");
			throw new MyException();
		}

		public  void main(String[] args){
			InheritingException sed = new InheritingException();
			try {
				sed.f();
			} catch (MyException e){
				e.printStackTrace();
			}




		}


	}

	/**
	 * 通过在定时执行的线程池，可以定时地执行一些任务
	 * @throws PendingIntent.CanceledException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private static void scheduledThreadPool() throws PendingIntent.CanceledException,
			ExecutionException, InterruptedException {
		//创建定时执行的线程池
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
		/**
		 *  参数2为第一次延迟的时间，参数3为执行周期
		 */
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("线程池：  执行线程：" + Thread.currentThread().getName()
					+ fibc(30));
			}
		}, 1, 2, TimeUnit.SECONDS);

		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("线程池：  执行线程：" + Thread.currentThread().getName()
						+ fibc(40));
			}
		}, 1, 2, TimeUnit.SECONDS);
	}

	private static int fibc(int num){

		if(num == 0){
			return 0;
		}
		if (num == 1) {
			return 1;
		}
		return fibc(num - 1) + fibc(num - 2);
	}

	/**
	 * 程序中的优化策略 -- CopyOnWriteArrayList
	 * Copy-On-Write  是一种延时懒惰策略
	 *
	 */





}
