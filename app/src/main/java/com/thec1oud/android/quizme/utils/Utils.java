package com.thec1oud.android.quizme.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.TypedValue;

public class Utils {
	public static float calculateAngle(PointF start, PointF end) {
		float deltaX = end.x - start.x;
		float deltaY = end.y - start.y;
		return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
	}

	public static int getColorFromThemeAttribute(Context context, int attribute) {
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(attribute, typedValue, true);

		return typedValue.data;
	}
}
