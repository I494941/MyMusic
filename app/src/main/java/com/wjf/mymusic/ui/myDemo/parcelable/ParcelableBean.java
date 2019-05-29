package com.wjf.mymusic.ui.myDemo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.wjf.mymusic.ui.myDemo.parcelable.bean.Book;

import java.util.ArrayList;

/**
 * @创建者 wjf
 * @创建时间 2019/5/24 14:25
 * @描述 ${TODO}
 */
public class ParcelableBean implements Parcelable {

    private int i;
    private String str;
    private boolean b;
    // ***** 注意: 这里如果是集合 ,一定要初始化 *****
    private ArrayList<Book> books = new ArrayList<>();

    public ParcelableBean() {

    }

    @Override
    public String toString() {
        return "ParcelableBean{" +
                "i=" + i +
                ", str='" + str + '\'' +
                ", b=" + b +
                ", books=" + books +
                '}';
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    protected ParcelableBean(Parcel in) {
        i = in.readInt();
        str = in.readString();
        b = in.readByte() != 0;
        books = in.createTypedArrayList(Book.CREATOR);
    }

    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel in) {
            return new ParcelableBean(in);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(i);
        dest.writeString(str);
        dest.writeByte((byte) (b ? 1 : 0));
        dest.writeTypedList(books);
    }
}
