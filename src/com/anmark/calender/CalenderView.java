package com.anmark.calender;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CalenderView extends View {

	private Calendar cal;
	private Bitmap mOrigBitmap, mScaledBitmap;
	private Paint paint;
	private int mOrigBitmapWidth, mOrigBitmapHeight;

	public CalenderView(Context context) {
		super(context);
		init(context);
	}

	public CalenderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalenderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		cal = Calendar.getInstance();

		// Create original mutable bitmap from drawable resource
		mOrigBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.calendar_sheet);
		mOrigBitmapWidth = 0;
		mOrigBitmapHeight = 0;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (this.getHeight() != mOrigBitmapHeight
				|| this.getWidth() != mOrigBitmapWidth) {
			// Create scaled bitmap from original bitmap
			mOrigBitmapHeight = this.getHeight();
			mOrigBitmapWidth = this.getWidth();
			mScaledBitmap = Bitmap.createScaledBitmap(mOrigBitmap,
					mOrigBitmapWidth, mOrigBitmapHeight, true);

			// Draw background bitmap
			canvas.drawBitmap(mScaledBitmap, 0, 0, null);
		} else {
			canvas.drawBitmap(mOrigBitmap, null,
					new Rect(0, 0, canvas.getWidth(), canvas.getHeight()),
					paint);
		}

		// Setup paint
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);

		// Draw black bottom day name
		paint.setTextSize(((canvas.getHeight()) / 180)
				* getResources().getDimension(R.dimen.small_text_size));
		canvas.drawText(getDayName(), canvas.getWidth() / 2f,
				canvas.getHeight() * .9f, paint);

		// Draw top white month name
		paint.setColor(Color.WHITE);
		canvas.drawText(getMonthName(), canvas.getWidth() / 2f,
				canvas.getHeight() * .4f, paint);

		// Draw black center date
		paint.setColor(Color.BLACK);
		paint.setTextSize(((canvas.getHeight()) / 100)
				* getResources().getDimension(R.dimen.small_text_size));
		canvas.drawText(Integer.toString(getDayNumber()),
				canvas.getWidth() / 2f, canvas.getHeight() * .72f, paint);

		// Draw black vertical right year
		paint.setTextSize(((canvas.getHeight()) / 180)
				* getResources().getDimension(R.dimen.small_text_size));
		canvas.rotate(90, canvas.getWidth() * .8f, canvas.getHeight() * .7f);
		canvas.drawText(Integer.toString(getYear()), canvas.getWidth() * .8f,
				canvas.getHeight() * .7f, paint);

		// Best practice to invalidate here?
		// postInvalidate();
	}

	public int getYear() {
		Integer year = cal.get(Calendar.YEAR);
		return year;
	}

	public String getMonthName() {
		String monthNames[] = new DateFormatSymbols().getMonths();
		String month = monthNames[cal.get(Calendar.MONTH)];
		return month = Character.toUpperCase(month.charAt(0))
				+ month.substring(1);
	}

	public String getDayName() {
		String dayNames[] = new DateFormatSymbols().getWeekdays();
		String day = dayNames[cal.get(Calendar.DAY_OF_WEEK)];
		return day = Character.toUpperCase(day.charAt(0)) + day.substring(1);
	}

	public int getDayNumber() {
		Integer dayNumber = cal.get(Calendar.DATE);
		return dayNumber;
	}

	public int nextDay() {
		cal.add(Calendar.DATE, 1);
		invalidate();
		return getDayNumber();
	}

	public void setDate(Calendar cal) {
		invalidate();
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
	 * /** Converts a immutable bitmap to a mutable bitmap. This operation
	 * doesn't allocates more memory that there is already allocated.
	 * 
	 * @param imgIn - Source image. It will be released, and should not be used
	 * more
	 * 
	 * @return a copy of imgIn, but muttable.
	 * 
	 * public static Bitmap convertToMutable(Bitmap imgIn) { try { //this is the
	 * file going to use temporally to save the bytes. // This file will not be
	 * a image, it will store the raw image data. File file = new
	 * File(Environment.getExternalStorageDirectory() + File.separator +
	 * "temp.tmp");
	 * 
	 * //Open an RandomAccessFile //Make sure you have added uses-permission
	 * android:name="android.permission.WRITE_EXTERNAL_STORAGE" //into
	 * AndroidManifest.xml file RandomAccessFile randomAccessFile = new
	 * RandomAccessFile(file, "rw");
	 * 
	 * // get the width and height of the source bitmap. int width =
	 * imgIn.getWidth(); int height = imgIn.getHeight(); Config type =
	 * imgIn.getConfig();
	 * 
	 * //Copy the byte to the file //Assume source bitmap loaded using
	 * options.inPreferredConfig = Config.ARGB_8888; FileChannel channel =
	 * randomAccessFile.getChannel(); MappedByteBuffer map =
	 * channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
	 * imgIn.copyPixelsToBuffer(map); //recycle the source bitmap, this will be
	 * no longer used. imgIn.recycle(); System.gc();// try to force the bytes
	 * from the imgIn to be released
	 * 
	 * //Create a new bitmap to load the bitmap again. Probably the memory will
	 * be available. imgIn = Bitmap.createBitmap(width, height, type);
	 * map.position(0); //load it back from temporary
	 * imgIn.copyPixelsFromBuffer(map); //close the temporary file and channel ,
	 * then delete that also channel.close(); randomAccessFile.close();
	 * 
	 * // delete the temp file file.delete();
	 * 
	 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * return imgIn; }
	 */
}
