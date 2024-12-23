package com.example.ecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecommerce.Models.Product;
import com.example.ecommerce.Models.User;

/**
 * Sqlite bağlanmak için kullanılan class.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
   private static final String DATABASE_NAME ="eCommerce.db";
    private static final int DATABASE_VERSION = 1;

    // user tablosu
    private static final String USERTABLE_NAME ="Users";
    private static final String USERTABLE_COLUMN_ID ="_id";
    private static final String USERTABLE_COLUMN_USERNAME ="username";
    private static final String USERTABLE_COLUMN_PASSWORD ="password";

    // ürün tablosu
    private  static final  String PRODUCTTABLE_NAME ="Porducts";
    private  static final  String PRODUCTTABLE_COLUMN_ID ="_id";
    private  static final  String PRODUCTTABLE_COLUMN_NAME ="name";
    private  static final  String PRODUCTTABLE_COLUMN_PRICE ="price";


    // stok miktarı (quantity) ve açıklama sadece detaylar kısmında gözükecek.
    private  static final  String PRODUCTTABLE_COLUMN_QUANTITY ="quantity";
    private  static final  String PRODUCTTABLE_COLUMN_DESCRIPTION ="description";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String queryForUsers ="CREATE TABLE "+ USERTABLE_NAME +" ( "+ USERTABLE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            USERTABLE_COLUMN_USERNAME +" TEXT,  " +
            USERTABLE_COLUMN_PASSWORD +" TEXT) ";

    String queryForProducts =" CREATE "+ PRODUCTTABLE_NAME + " ( " +PRODUCTTABLE_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            PRODUCTTABLE_COLUMN_NAME+" TEXT, "+
            PRODUCTTABLE_COLUMN_PRICE+" INTEGER, "+
            PRODUCTTABLE_COLUMN_QUANTITY+" INTEGER,"+
            PRODUCTTABLE_COLUMN_DESCRIPTION+" TEXT )";

        db.execSQL(queryForUsers);
        db.execSQL(queryForProducts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USERTABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ PRODUCTTABLE_NAME);
        onCreate(db);
    }


    /**
     * veritabanına user ekleme işlemi yapar.
     * @param user
     * eklenecek user
     */
    public void addUser(@NonNull User user){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERTABLE_COLUMN_USERNAME,user.getUserName());
        contentValues.put(USERTABLE_COLUMN_PASSWORD,user.getPassword());

       long result = db.insert(USERTABLE_NAME,null,contentValues);

        if (result == -1){
            Toast.makeText(context,"Ekleme hatası.",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Ekleme işlemi başarılı.",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * tüm kullanıcıları getirir.
     */
    public Cursor readAllUsers(){
        String query = "SELECT * FROM "+ USERTABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }

    /**
     * Veritabanı bağlantısını kontrol eder.
     * @return
     * bağlantı varsa true döner yoksa false döner.
     */
    public boolean checkConnection(){
        SQLiteDatabase db = this.getReadableDatabase();
        return  db != null;
    }

    /**
     * UserName göre filterleme yapar.
     * @return
     */
    public Cursor readUserByUserName(String userName){
        String query = "SELECT * FROM "+ USERTABLE_NAME + " WHERE "+ USERTABLE_COLUMN_USERNAME +"  =  ? ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query, new String[]{userName});
        }
        return  cursor;
    }

    /**
     *  Veritabanındaki tüm ürünleri okur.
     */
    public Cursor readAllProducts(){
        String query = "SELECT * FROM "+ PRODUCTTABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery("select * from Users",null);
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }

    /**
     * Id bazlı filtreleme yapar ürünler için.
     */
    public Cursor readProductById(int productId){

        String query = "SELECT * FROM "+ PRODUCTTABLE_NAME + " WHERE "+ PRODUCTTABLE_COLUMN_ID +"  =  ? ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});
        }
        return  cursor;
    }

    /**
     * veritabanına ünrün ekler.
     */
    public void addProduct(Product product){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PRODUCTTABLE_COLUMN_NAME,product.getName());
        contentValues.put(PRODUCTTABLE_COLUMN_DESCRIPTION,product.getDescription());
        contentValues.put(PRODUCTTABLE_COLUMN_PRICE,product.getPrice());
        contentValues.put(PRODUCTTABLE_COLUMN_QUANTITY,product.getQuantity());

        long result = db.insert(PRODUCTTABLE_NAME,null,contentValues);

        if (result == -1){
            Toast.makeText(context,"Ekleme hatası.",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Ekleme işlemi başarılı.",Toast.LENGTH_SHORT).show();
        }
    }

}
