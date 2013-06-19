package com.anmark.calender;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CopyOfCalenderView extends View {

	private Calendar cal;
	private Date date;
	private Paint paint;

	public CopyOfCalenderView(Context context) {
		super(context);
		this.setBackgroundResource(R.drawable.calendar_sheet);
		paint = new Paint();
		cal = Calendar.getInstance();
		System.out.println(getMonthName(cal));
		System.out.println(getDayName(cal));
		System.out.println(getDayNumber(cal));
		//System.out.println(date.toString());af
	}

	public CopyOfCalenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CopyOfCalenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.calendar_sheet);

		//canvas.setBitmap(bitmap);
		/*
		paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		canvas.drawText(cal.toString(), 75, 110, paint);
		 */



	}


	public String getMonthName(Calendar cal){
		String monthNames[] = new DateFormatSymbols().getMonths();
		String month = monthNames[cal.get(Calendar.MONTH)];		 
		return month = Character.toUpperCase(month.charAt(0)) + month.substring(1);
	}

	public String getDayName(Calendar cal){
		String dayNames[] = new DateFormatSymbols().getWeekdays();
		String day = dayNames[cal.get(Calendar.DAY_OF_WEEK)];
		return day = Character.toUpperCase(day.charAt(0)) + day.substring(1);
	}
	public int getDayNumber(Calendar cal){
		Integer dayNumber = cal.get(Calendar.DATE);
		return dayNumber;
	}
}






