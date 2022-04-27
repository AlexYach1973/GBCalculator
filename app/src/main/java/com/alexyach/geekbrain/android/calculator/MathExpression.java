package com.alexyach.geekbrain.android.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class MathExpression implements Parcelable  {
    private Double current1;
    private Double current2;
    private String operation;
    private String story;
    private String display;

    protected MathExpression(Parcel in) {
        if (in.readByte() == 0) {
            current1 = null;
        } else {
            current1 = in.readDouble();
        }
        if (in.readByte() == 0) {
            current2 = null;
        } else {
            current2 = in.readDouble();
        }
        operation = in.readString();
        story = in.readString();
        display = in.readString();
    }

    public static final Creator<MathExpression> CREATOR = new Creator<MathExpression>() {
        @Override
        public MathExpression createFromParcel(Parcel in) {
            return new MathExpression(in);
        }

        @Override
        public MathExpression[] newArray(int size) {
            return new MathExpression[size];
        }
    };

    public Double getCurrent1() {
        return current1;
    }

    public void setCurrent1(Double current1) {
        this.current1 = current1;
    }

    public Double getCurrent2() {
        return current2;
    }

    public void setCurrent2(Double current2) {
        this.current2 = current2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public MathExpression(Double current1, Double current2, String operation, String story, String display) {
        this.current1 = current1;
        this.current2 = current2;
        this.operation = operation;
        this.story = story;
        this.display = display;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (current1 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(current1);
        }
        if (current2 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(current2);
        }
        parcel.writeString(operation);
        parcel.writeString(story);
        parcel.writeString(display);
    }
}
