package com.anmark.calender;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CalenderView extends View {

	private Calendar cal;
	private Paint paint;

	public CalenderView(Context context) {
		super(context);
		
		init();
		
		/*
		System.out.println(getYear());
		System.out.println(getMonthName());
		System.out.println(getDayName());
		System.out.println(getDayNumber());

		setDate(createCalender(2000, 1, 30));

		System.out.println("");
		System.out.println(getYear());
		System.out.println(getMonthName());
		System.out.println(getDayName());
		System.out.println(getDayNumber());
		 */
	}

	public CalenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CalenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		//TODO:
		cal = Calendar.getInstance();
		
		//this.setBackgroundResource(R.drawable.calendar_sheet);
	}


	@SuppressWarnings("deprecation")
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.calendar_sheet);
		//canvas.drawColor(Color.WHITE);

		/*
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(24);
		*/
		Paint paint = new Paint();
		paint.setAntiAlias(true);
	
		
		
		//Bitmap b = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		
		canvas.drawBitmap(bitmap, null, new Rect(0,0,getWidth(), getHeight()), paint);
		
		paint.setTextSize(100);
		canvas.drawText(getDayName(), canvas.getWidth()*.5f/2f, canvas.getHeight()*.9f, paint);
	
		canvas.drawText(getMonthName(), canvas.getWidth()*.7f/2f, canvas.getHeight()*.4f, paint);
		
		paint.setTextSize(200);
		canvas.drawText(Integer.toString(getDayNumber()), canvas.getWidth()*.65f/2f, canvas.getHeight()*.75f, paint);
		
		

		//Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.calendar_sheet);
		//canvas.drawBitmap(bitmap, canvas.getMatrix(), paint);
		//Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, canvas.getWidth(), canvas.getHeight(), true);
		//bitmap.recycle();
		//Bitmap convertedBitmap = convertToMutable(bitmap);

		//canvas.drawBitmap(bitmap, null, new Rect(0,0,getWidth(), getHeight()), paint);

		//canvas.drawText(getDayName(), getWidth()/2, getHeight()/2, paint);

		/*	@SuppressWarnings("deprecation")
		Drawable drawable = new BitmapDrawable(bitmapScaled);

		image.setBackgroundDrawable(drawable);
		 */
		//canvas.drawBitmap(bitmap, null, null, paint);

		//Bitmap background = Bitmap.createScaledBitmap(bitmap, this.getWidth(), this.getHeight(), false);

		//canvas.drawBitmap(bitmap, 0, 0, null);


		//canvas.drawBitmap(bitmap,  bitmap.getWidth() - (bitmap.getWidth() / 2), bitmap.getHeight() - (bitmap.getHeight() / 2), null);


		//canvas.drawBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), paint);

		//canvas.drawBitmap(bitmap);


		//paint.setStyle(Paint.Style.FILL);
		//turn antialiasing on
		//paint.setAntiAlias(true);
		//paint.setTextSize(30);
		//canvas.drawText(cal.toString(), 75, 110, paint);


		//
		//canvas.save();
		// every save() must be matched with restore()
		//canvas.restore();

	}
	public int getYear(){
		Integer year = cal.get(Calendar.YEAR);
		return year;
	}
	public String getMonthName(){
		String monthNames[] = new DateFormatSymbols().getMonths();
		String month = monthNames[cal.get(Calendar.MONTH)];		 
		return month = Character.toUpperCase(month.charAt(0)) + month.substring(1);
	}

	public String getDayName(){
		String dayNames[] = new DateFormatSymbols().getWeekdays();
		String day = dayNames[cal.get(Calendar.DAY_OF_WEEK)];
		return day = Character.toUpperCase(day.charAt(0)) + day.substring(1);
	}
	public int getDayNumber(){
		Integer dayNumber = cal.get(Calendar.DATE);
		return dayNumber;
	}	
	public int nextDay(){
		cal.add(Calendar.DATE, 1);
		return getDayNumber();
	}

	public void setDate(Calendar cal){
		this.cal = cal;
	}
	
	public Calendar createCalender(int year, int month, int day) {
		Calendar newCal = Calendar.getInstance();
		newCal.set(Calendar.YEAR, year);
		newCal.set(Calendar.MONTH, month);
		newCal.set(Calendar.DAY_OF_MONTH, day);
		return newCal;
	}
	
	
	/*
	/**
	 * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
	 * more memory that there is already allocated.
	 * 
	 * @param imgIn - Source image. It will be released, and should not be used more
	 * @return a copy of imgIn, but muttable.
	 *
	public static Bitmap convertToMutable(Bitmap imgIn) {
		try {
			//this is the file going to use temporally to save the bytes. 
			// This file will not be a image, it will store the raw image data.
			File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

			//Open an RandomAccessFile
			//Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
			//into AndroidManifest.xml file
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

			// get the width and height of the source bitmap.
			int width = imgIn.getWidth();
			int height = imgIn.getHeight();
			Config type = imgIn.getConfig();

			//Copy the byte to the file
			//Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
			FileChannel channel = randomAccessFile.getChannel();
			MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
			imgIn.copyPixelsToBuffer(map);
			//recycle the source bitmap, this will be no longer used.
			imgIn.recycle();
			System.gc();// try to force the bytes from the imgIn to be released

			//Create a new bitmap to load the bitmap again. Probably the memory will be available. 
			imgIn = Bitmap.createBitmap(width, height, type);
			map.position(0);
			//load it back from temporary 
			imgIn.copyPixelsFromBuffer(map);
			//close the temporary file and channel , then delete that also
			channel.close();
			randomAccessFile.close();

			// delete the temp file
			file.delete();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return imgIn;
	}
	*/
}






