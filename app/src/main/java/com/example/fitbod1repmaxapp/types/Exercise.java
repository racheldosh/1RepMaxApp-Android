package com.example.fitbod1repmaxapp.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Exercise implements Parcelable {
    public String name;
    public Date date;
    public int repMax;

    public Exercise(String dataInput) {
        String[] dataSplit = dataInput.split(",");

        this.name = dataSplit[1];

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        try {
            this.date = sdf.parse(dataSplit[0]);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }

        this.repMax = calcRepMax(dataSplit[4], dataSplit[3]);
    }

    public Exercise(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public void readFromParcel(Parcel in) {
        this.date = (java.util.Date) in.readSerializable();
        this.name = in.readString();
        this.repMax = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.date);
        dest.writeString(this.name);
        dest.writeInt(this.repMax);
    }

    private int calcRepMax(String weight, String reps) {
        return (Integer.parseInt(weight) * (36 / (37 - Integer.parseInt(reps))));
    }
}
