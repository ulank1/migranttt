package com.example.admin.pagination.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.admin.pagination.Serializables.Consulate;
import com.example.admin.pagination.Serializables.Diaspora;
import com.example.admin.pagination.Serializables.EAEU;
import com.example.admin.pagination.Serializables.Embassy;
import com.example.admin.pagination.Serializables.Employment;
import com.example.admin.pagination.Serializables.Hotline;
import com.example.admin.pagination.Serializables.Istories;
import com.example.admin.pagination.Serializables.NKO;
import com.example.admin.pagination.Serializables.RulesOfIncoming;
import com.example.admin.pagination.Serializables.User;
import com.example.admin.pagination.Serializables.VseAndUzery;

/**
 * Created by Admin on 12.07.2016.
 */
public class DataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String TABLE_USER="User";
    public static final String USER_ID_COLUMN="user_id";



    public static final String TABLE_EMPLOYMENT="Employment";
    public static final String EMPLOYMENT_NAME_COLUMN="employment_name";
    public static final String EMPLOYMENT_ADDRESS_COLUMN="employment_address";
    public static final String EMPLOYMENT_MANAGER_COLUMN="employment_manager";
    public static final String EMPLOYMENT_NUMBER_COLUMN="employment_number";
    public static final String EMPLOYMENT_NUMBER1_COLUMN="employment_number1";
    public static final String EMPLOYMENT_NUMBER2_COLUMN="employment_number2";
    public static final String EMPLOYMENT_PARENT_ID_COLUMN="employment_parent_id";





    public static final String TABLE_NEWS="News";
    public static final String NEWS_ZAGOLOVOK_COLUMN="news_zagolovok";
    public static final String NEWS_TEXT_COLUMN="news_text";
    public static final String NEWS_IMAGE_COLUMN="news_image";


    public static final String TABLE_RULES_OF_INCOMING="Rules_of_incoming";
    public static final String RULES_OF_INCOMING_ZAGOLOVOK_COLUMN="rules_of_zagolovok";
    public static final String RULES_OF_INCOMING_TEXT_COLUMN="rules_of_text";
    public static final String RULES_OF_INCOMING_IMAGE_COLUMN="rules_of_image";
    public static final String RULES_OF_INCOMING_PARENT_ID_COLUMN="rules_of_parent_id";



    public static final String TABLE_HT="HT";
    public static final String HT_ZAGOLOVOK_COLUMN="ht_zagolovok";
    public static final String HT_TEXT_COLUMN="ht_text";
    public static final String HT_IMAGE_COLUMN="ht_image";


    public static final String TABLE_DIASPORA="Diaspora";
    public static final String DIASPORA_PARENT_ID_COLUMN="diaspora_parent_id";
    public static final String DIASPORA_city_COLUMN="diaspora_city";
    public static final String DIASPORA_ADDRESS_COLUMN="diaspora_address";
    public static final String DIASPORA_EMAIL_COLUMN="diaspora_email";
    public static final String DIASPORA_MANAGER_COLUMN="diaspora_manager";
    public static final String DIASPORA_NUMBER_COLUMN="diaspora_number";
    public static final String DIASPORA_PLACE_COLUMN="diaspora_place";



    public static final String TABLE_PROHIB="Prohibition";
    public static final String PROHIB_ZAGOLOVOK_COLUMN="Prohibition_zagolovok";
    public static final String PROHIB_TEXT_COLUMN="Prohibition_text";
    public static final String PROHIB_IMAGE_COLUMN="Prohibition_image";


    public static final String TABLE_RULES_OF_MIGRATION="Rules_of_migration";
    public static final String RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN="rules_of_zagolovok";
    public static final String RULES_OF_MIGRATION_TEXT_COLUMN="rules_of_migration_text";
    public static final String RULES_OF_MIGRATION_IMAGE_COLUMN="rules_of_migration_image";
    public static final String RULES_OF_MIGRATION_PARENT_ID_COLUMN="rules_of_migration_imageparent_id";


    public static final String  NEWS_JSON_ID_COLUMN="news_id_json";


    public static final String TABLE_LANGUAGE="Language";
    public static final String LANGUAGE_COLUMN="news_zagolovok";



    public static final String TABLE_EMBASSY="Embassy";
    public static final String EMBASSY_COUNTRY_COLUMN="embassy_country";
    public static final String EMBASSY_ADDRESS_COLUMN="em_address";
    public static final String EMBASSY_PHONE_COLUMN="em_phone";
    public static final String EMBASSY_SITE_COLUMN="em_site";
    public static final String EMBASSY_JSON_ID_COLUMN="em_id";
    public static final String EMBASSY_EMAIL_COLUMN="em_email";
    public static final String EMBASSY_FAX_COLUMN="em_fax";
    public static final String EMBASSY_IMAGE_COLUMN="em_image";

    public static final String TABLE_HOT_LINE="Hot_line";
    public static final String HOT_TITLE_COLUMN="hot_zagolovok";
    public static final String HOT_PHONE_COLUMN="hot_phone";
    public static final String HOT_DESCRIPTION_COLUMN="hot_description";
    public static final String HOT_PARENT_ID_COLUMN="hot_parent_id";


    public static final String TABLE_CONSULATE="Consulate";
    public static final String CONSULATE_REGION_COLUMN="consulate_region";
    public static final String CONSULATE_PHONE_COLUMN="consulate_phone";
    public static final String CONSULATE_ADDRESS_COLUMN="consulate_address";
    public static final String CONSULATE_PARENT_ID_COLUMN="consulate_parent_id";

    public static final String TABLE_FAQ="Faq";
    public static final String FAQ_ANSWER_COLUMN="faq_answer";
    public static final String FAQ_QUESTION_COLUMN="faq_question";

    public static final String TABLE_FORUM="Forum";
    public static final String FORUM_TITLE_COLUMN="forum_title";
    public static final String FORUM_USERNAME_COLUMN="forum_username";
    public static final String FORUM_TEXT_COLUMN="forum_text";

    public static final String TABLE_QUESTION_ANSWER="Qa";
    public static final String QA_USERNAME_COLUMN="qa_username";
    public static final String QA_QUESTION_COLUMN="qa_question";
    public static final String QA_ID_COLUMN="a_id";

    public static final String TABLE_ANSWER="Answer";
    public static final String A_USERNAME_COLUMN="a_username";
    public static final String A_ANSWER_COLUMN="a_question";
    public static final String A_ID_COLUMN="a_id";

    public static final String TABLE_HOT_COUNTRY="Hot_country";
    public static final String HOT_COUNTRY_TITLE_COLUMN="title_hot_country";
    public static final String HOT_COUNTRY_PICTURE_COLUMN="hot_country_picture";
    public static final String HOT_COUNTRY_ID_COLUMN="hot_country_id";


    public static final String TABLE_EAEU="EAEU";
    public static final String EAEU_TITLE_COLUMN="title_eaeu";
    public static final String EAEU_PICTURE_COLUMN="eaeu_picture";
    public static final String EAEU_ID_COLUMN="eaeu_id";


    public static final String TABLE_NKOC="NKOC";
    public static final String NKOC_TITLE_COLUMN="nkoc_eaeu";
    public static final String NKOC_PICTURE_COLUMN="nkoc_picture";
    public static final String NKOC_ID_COLUMN="nkoc_id";


    public static final String TABLE_NKO="NKO";
    public static final String NKO_ADDRESS_COLUMN="nko_address";
    public static final String NKO_MAIL_COLUMN="nko_mail";
    public static final String NKO_MANAGER_COLUMN="nko_manager";
    public static final String NKO_PHONE_COLUMN="nko_phone";
    public static final String NKO_PHONE1_COLUMN="nko_phone1";
    public static final String NKO_PARENT_ID_COLUMN="nko_parent_id";
    public static final String NKO_TEXT_COLUMN="nko_text";
    public static final String NKO_TITLE_COLUMN="nko_title";


    public static final String TABLE_DIAS="Dias";
    public static final String DIAS_TITLE_COLUMN="title_dias";
    public static final String DIAS_PICTURE_COLUMN="dias_picture";
    public static final String DIAS_ID_COLUMN="dias_id";


    public static final String TABLE_DATE="Date";
    public static final String DATE_LAST_DATE_COLUMN="last_date";

    public static final String TABLE_SIZE="Size";
    public static final String SIZE_WIDTH_COLUMN="size_width";
    public static final String SIZE_HEIGHT_COLUMN="size_height";

    public static final String TABLE_EMPLOY="Employ";
    public static final String EMPLOY_TITLE_COLUMN="title_employ";
    public static final String EMPLOY_PICTURE_COLUMN="employ_picture";
    public static final String EMPLOY_ID_COLUMN="employ_id";


    public static final String TABLE_ABROAD="Abroad";
    public static final String ABROAD_TITLE_COLUMN="title_abroad";
    public static final String ABROAD_PICTURE_COLUMN="employ_abroad";
    public static final String ABROAD_ID_COLUMN="employ_id";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NEWS + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                NEWS_ZAGOLOVOK_COLUMN + " text," +
                NEWS_IMAGE_COLUMN + " text," +
                NEWS_TEXT_COLUMN + " text);");

        db.execSQL("create table " + TABLE_FAQ + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                FAQ_ANSWER_COLUMN + " text," +
                FAQ_QUESTION_COLUMN + " text);");

        db.execSQL("create table " + TABLE_SIZE + "(" +

                SIZE_HEIGHT_COLUMN + " integer," +
                SIZE_WIDTH_COLUMN + " integer);");

        db.execSQL("create table " + TABLE_EAEU + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                EAEU_PICTURE_COLUMN + " text," +
                EAEU_ID_COLUMN + " text," +
                EAEU_TITLE_COLUMN + " text);");


        db.execSQL("create table " + TABLE_NKOC + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                NKOC_ID_COLUMN + " text," +
                NKOC_PICTURE_COLUMN + " text," +
                NKOC_TITLE_COLUMN + " text);");


        db.execSQL("create table " + TABLE_NKO + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                NKO_ADDRESS_COLUMN + " text," +
                NKO_MAIL_COLUMN + " text," +
                NKO_PARENT_ID_COLUMN + " text," +
                NKO_TEXT_COLUMN + " text," +
                NKO_PHONE_COLUMN + " text," +
                NKO_PHONE1_COLUMN + " text," +
                NKO_TITLE_COLUMN + " text," +
                NKO_MANAGER_COLUMN + " text);");


        db.execSQL("create table " + TABLE_HOT_COUNTRY + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                HOT_COUNTRY_PICTURE_COLUMN + " text," +
                HOT_COUNTRY_TITLE_COLUMN + " text," +
                HOT_COUNTRY_ID_COLUMN + " text);");


        db.execSQL("create table " + TABLE_DIAS + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                DIAS_TITLE_COLUMN + " text," +
                DIAS_ID_COLUMN + " text," +
                DIAS_PICTURE_COLUMN + " text);");



        db.execSQL("create table " + TABLE_LANGUAGE + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                LANGUAGE_COLUMN + " integer);");


        db.execSQL("create table " + TABLE_DATE + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                DATE_LAST_DATE_COLUMN + " text);");


        db.execSQL("create table " + TABLE_DIASPORA + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                DIASPORA_ADDRESS_COLUMN + " text," +
                DIASPORA_EMAIL_COLUMN + " text," +
                DIASPORA_PARENT_ID_COLUMN + " text," +
                DIASPORA_MANAGER_COLUMN + " text," +
                DIASPORA_NUMBER_COLUMN + " text," +
                DIASPORA_PLACE_COLUMN + " text," +
                DIASPORA_city_COLUMN + " text);");


        db.execSQL("create table " + TABLE_ABROAD + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                ABROAD_PICTURE_COLUMN + " text," +
                ABROAD_ID_COLUMN + " text," +
                ABROAD_TITLE_COLUMN + " text);");

        db.execSQL("create table " + TABLE_EMPLOY + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
               EMPLOY_PICTURE_COLUMN + " text," +
               EMPLOY_ID_COLUMN + " text," +
                EMPLOY_TITLE_COLUMN + " text);");

        db.execSQL("create table " + TABLE_RULES_OF_INCOMING + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                RULES_OF_INCOMING_IMAGE_COLUMN + " text," +
                RULES_OF_INCOMING_ZAGOLOVOK_COLUMN + " text," +
                RULES_OF_INCOMING_PARENT_ID_COLUMN + " text," +
                RULES_OF_INCOMING_TEXT_COLUMN + " text);");


        db.execSQL("create table " + TABLE_HT + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                HT_IMAGE_COLUMN + " text," +
                HT_TEXT_COLUMN + " text," +
                HT_ZAGOLOVOK_COLUMN + " text);");


        db.execSQL("create table " + TABLE_PROHIB + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                PROHIB_IMAGE_COLUMN + " text," +
                PROHIB_TEXT_COLUMN + " text," +
                PROHIB_ZAGOLOVOK_COLUMN + " text);");



        db.execSQL("create table " + TABLE_EMPLOYMENT + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                EMPLOYMENT_ADDRESS_COLUMN + " text," +
                EMPLOYMENT_NUMBER1_COLUMN + " text," +
                EMPLOYMENT_NUMBER2_COLUMN + " text," +
                EMPLOYMENT_PARENT_ID_COLUMN + " text," +
                EMPLOYMENT_NUMBER_COLUMN + " text," +
                EMPLOYMENT_MANAGER_COLUMN + " text," +
                EMPLOYMENT_NAME_COLUMN + " text);");



        db.execSQL("create table " + TABLE_HOT_LINE + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                HOT_DESCRIPTION_COLUMN + " text," +
                HOT_PHONE_COLUMN + " text," +
                HOT_PARENT_ID_COLUMN + " text," +
                HOT_TITLE_COLUMN + " text);");


        db.execSQL("create table " + TABLE_RULES_OF_MIGRATION + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                RULES_OF_MIGRATION_IMAGE_COLUMN + " text," +
                RULES_OF_MIGRATION_PARENT_ID_COLUMN + " text," +
                RULES_OF_MIGRATION_TEXT_COLUMN + " text," +
                RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN + " text);");

        db.execSQL("create table " + TABLE_CONSULATE + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                CONSULATE_ADDRESS_COLUMN + " text," +
                CONSULATE_PHONE_COLUMN + " text," +
                CONSULATE_PARENT_ID_COLUMN + " text," +
                CONSULATE_REGION_COLUMN + " text);");

        db.execSQL("create table " + TABLE_EMBASSY + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                EMBASSY_ADDRESS_COLUMN + " text," +
                EMBASSY_COUNTRY_COLUMN + " text," +
                EMBASSY_EMAIL_COLUMN + " text," +
                EMBASSY_IMAGE_COLUMN + " text," +
                EMBASSY_FAX_COLUMN + " text," +
                EMBASSY_JSON_ID_COLUMN + " text," +
                EMBASSY_PHONE_COLUMN + " text," +
                EMBASSY_SITE_COLUMN + " text);");

        db.execSQL("create table " + TABLE_FORUM + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                FORUM_TITLE_COLUMN + " text," +
                FORUM_TEXT_COLUMN + " text," +
                FORUM_USERNAME_COLUMN + " text);");

        db.execSQL("create table " + TABLE_QUESTION_ANSWER + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                QA_QUESTION_COLUMN + " text," +
                QA_ID_COLUMN + " text," +
                QA_USERNAME_COLUMN + " text);");

        db.execSQL("create table " + TABLE_ANSWER + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +
                A_ANSWER_COLUMN + " text," +
                A_ID_COLUMN + " text," +
                A_USERNAME_COLUMN + " text);");
        db.execSQL("create table " + TABLE_USER + "(" +
                BaseColumns._ID + " integer primary key autoincrement," +

                USER_ID_COLUMN + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Language>---------------------------

    public Cursor getDataLanguage() {
        return getReadableDatabase().query(TABLE_LANGUAGE,
                null, null, null,
                null, null, null);
    }
    public void insertLanguage(int id) {
        ContentValues values = new ContentValues();

        values.put(LANGUAGE_COLUMN, id);

        getWritableDatabase().insert(TABLE_LANGUAGE, null, values);

    }
    public void deleteLanguage() {
        getWritableDatabase().delete(TABLE_LANGUAGE, null, null);
    }


    //DATE>---------------------------

    public Cursor getDataDate(String id) {
        return getReadableDatabase().query(TABLE_DATE,
                null, BaseColumns._ID+ " = ? ", new String[]{String.valueOf(id)}, null,
                null, null);
    }
    public Cursor getDataDate1() {
        return getReadableDatabase().query(TABLE_DATE,
                null, null, null,
                null, null,null);
    }
    public void insertDate(String date) {
        ContentValues values = new ContentValues();

        values.put(DATE_LAST_DATE_COLUMN, date);

        getWritableDatabase().insert(TABLE_DATE, null, values);

    }
    public void updateDate(String date,String position){
        ContentValues values = new ContentValues();

        values.put(DATE_LAST_DATE_COLUMN, date);

        getWritableDatabase().update(TABLE_DATE,values,BaseColumns._ID+" = ? ",new String[]{String.valueOf(position)});
    }
    public void deleteDate(String id) {
        getWritableDatabase().delete(TABLE_DATE, BaseColumns._ID+" = "+id, null);
    }
    public void deleteDate1() {
        getWritableDatabase().delete(TABLE_DATE, null, null);
    }


    //User>---------------------------------------------------------------------------


    public Cursor getDataUser() {
        return getReadableDatabase().query(TABLE_USER,
                null, null, null,
                null, null, null);
    }
    public void insertUser(String id) {
        ContentValues values = new ContentValues();

        values.put(USER_ID_COLUMN, id);

        getWritableDatabase().insert(TABLE_USER, null, values);

    }
    public void deleteUser() {
        getWritableDatabase().delete(TABLE_USER, null, null);
    }

    public void readDataUser(){
        Cursor cursor=getDataUser();
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Log.e("TAG_DATAHELPER_S",cursor.getString(cursor.getColumnIndex(USER_ID_COLUMN)));
            }
        }
    }




    //News>----------------------------------------------------------------------
    public Cursor getData() {
        return getReadableDatabase().query(TABLE_NEWS,
                null, null, null,
                null, null, null);
    }
    public void insertNews(Istories istories) {
        ContentValues values = new ContentValues();

        values.put(NEWS_ZAGOLOVOK_COLUMN, istories.getNickName());
        values.put(NEWS_TEXT_COLUMN,istories.getText());
        values.put(NEWS_IMAGE_COLUMN,istories.getImage());


        getWritableDatabase().insert(TABLE_NEWS, null, values);

    }
    public void delete() {
        getWritableDatabase().delete(TABLE_NEWS, null, null);
    }
    public void readData(){
        Cursor cursor=getData();
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Log.e("TAG_DATAHELPER",cursor.getString(cursor.getColumnIndex(NEWS_TEXT_COLUMN)));
            }
        }
    }

    //Rules of incoming>------------------------------------------------------------------


    public Cursor getDataROI(String id) {
        return getReadableDatabase().query(TABLE_RULES_OF_INCOMING,
                null, RULES_OF_INCOMING_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)},
                null, null, null);
    }
    public void insertROI(RulesOfIncoming istories,String id) {
        ContentValues values = new ContentValues();

        values.put(RULES_OF_INCOMING_ZAGOLOVOK_COLUMN, istories.getTitle());
        values.put(RULES_OF_INCOMING_TEXT_COLUMN,istories.getText());
        values.put(RULES_OF_INCOMING_IMAGE_COLUMN,istories.getImage());
        values.put(RULES_OF_INCOMING_PARENT_ID_COLUMN,id);


        getWritableDatabase().insert(TABLE_RULES_OF_INCOMING, null, values);

    }
    public void deleteROI() {
        getWritableDatabase().delete(TABLE_RULES_OF_INCOMING, null, null);
    }

//Diaspora>------------------------------------------------------------

    public Cursor getDataDiaspora(String id) {
        return getReadableDatabase().query(TABLE_DIASPORA, null,DIASPORA_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)}, null, null, null);

    }
    public Cursor getDataDiaspora1(String id) {
        return getReadableDatabase().query(TABLE_DIASPORA, null,null,null, null, null, null);

    }
    public void insertDiaspora(Diaspora istories,String pos) {
        ContentValues values = new ContentValues();

        values.put(DIASPORA_ADDRESS_COLUMN, istories.getAddress());
        values.put(DIASPORA_city_COLUMN, istories.getCity());
        values.put(DIASPORA_EMAIL_COLUMN, istories.getEmail());
        values.put(DIASPORA_MANAGER_COLUMN, istories.getManager());
        values.put(DIASPORA_NUMBER_COLUMN, istories.getNumber());
        values.put(DIASPORA_PLACE_COLUMN, istories.getPlace());
        values.put(DIASPORA_PARENT_ID_COLUMN, pos);



        getWritableDatabase().insert(TABLE_DIASPORA, null, values);

    }
    public void deleteDiaspora() {
        getWritableDatabase().delete(TABLE_DIASPORA, null, null);
    }



    //HT--------------------------------------------------->

    public Cursor getDataProhibition() {
        return getReadableDatabase().query(TABLE_PROHIB,
                null, null, null,
                null, null, null);
    }
    public void insertProhibition(RulesOfIncoming istories) {
        ContentValues values = new ContentValues();

        values.put(PROHIB_ZAGOLOVOK_COLUMN, istories.getTitle());
        values.put(PROHIB_TEXT_COLUMN,istories.getText());
        values.put(PROHIB_IMAGE_COLUMN,istories.getImage());

        getWritableDatabase().insert(TABLE_PROHIB, null, values);

    }



    public void deleteProhibition() {
        getWritableDatabase().delete(TABLE_PROHIB, null, null);
    }
    //Prohibition>--------------------------------------------------------

    public Cursor getDataHT() {
        return getReadableDatabase().query(TABLE_HT,
                null, null, null,
                null, null, null);
    }
    public void insertHT(RulesOfIncoming istories) {
        ContentValues values = new ContentValues();

        values.put(HT_ZAGOLOVOK_COLUMN, istories.getTitle());
        values.put(HT_TEXT_COLUMN,istories.getText());
        values.put(HT_IMAGE_COLUMN,istories.getImage());

        getWritableDatabase().insert(TABLE_HT, null, values);

    }



    public void deleteHT() {
        getWritableDatabase().delete(TABLE_HT, null, null);
    }


    //Rules of Migration>-----------------------------------------------------------------------


    public Cursor getDataROM(String id) {
        return getReadableDatabase().query(TABLE_RULES_OF_MIGRATION,
                null, RULES_OF_MIGRATION_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)},
                null, null, null);
    }
    public void insertROM(RulesOfIncoming ofIncoming,String id) {
        ContentValues values = new ContentValues();

        values.put(RULES_OF_MIGRATION_ZAGOLOVOK_COLUMN, ofIncoming.getTitle());
        values.put(RULES_OF_MIGRATION_PARENT_ID_COLUMN, id);
        values.put(RULES_OF_MIGRATION_TEXT_COLUMN,ofIncoming.getText());
        values.put(RULES_OF_MIGRATION_IMAGE_COLUMN,ofIncoming.getImage());

        getWritableDatabase().insert(TABLE_RULES_OF_MIGRATION, null, values);

    }
    public void deleteROM() {
        getWritableDatabase().delete(TABLE_RULES_OF_MIGRATION, null, null);
    }


    //Embassy>--------------------------------------------------------------------------------------


    public Cursor getDataEmbassy() {
        return getReadableDatabase().query(TABLE_EMBASSY,
                null, null, null,
                null, null, null);
    }
    public void insertEmbassy(Embassy embassy) {
        ContentValues values = new ContentValues();

        values.put(EMBASSY_FAX_COLUMN, embassy.getFax());
        values.put(EMBASSY_ADDRESS_COLUMN,embassy.getRegion());
        values.put(EMBASSY_COUNTRY_COLUMN,embassy.getCountry());
        values.put(EMBASSY_SITE_COLUMN, embassy.getSite());
        values.put(EMBASSY_EMAIL_COLUMN,embassy.getEmail());
        values.put(EMBASSY_JSON_ID_COLUMN,embassy.getId());
        values.put(EMBASSY_PHONE_COLUMN, embassy.getPhoneNumber());
        values.put(EMBASSY_IMAGE_COLUMN, embassy.getImage());


        getWritableDatabase().insert(TABLE_EMBASSY, null, values);

    }
    public void deleteEmbassy() {
        getWritableDatabase().delete(TABLE_EMBASSY, null, null);
    }

    //HOTLINE>-------------------------------------------------------------------------------------
    public Cursor getDataHot(String id) {
        return getReadableDatabase().query(TABLE_HOT_LINE,
                null, HOT_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)},
                null, null, null);
    }
    public void insertHot(Hotline hotline,String id) {
        ContentValues values = new ContentValues();

        values.put(HOT_TITLE_COLUMN, hotline.getTitle());
        values.put(HOT_PARENT_ID_COLUMN, id);
        values.put(HOT_DESCRIPTION_COLUMN,hotline.getDescription());
        values.put(HOT_PHONE_COLUMN,hotline.getPhoneNumber());

        getWritableDatabase().insert(TABLE_HOT_LINE, null, values);

    }
    public void deleteHot() {
        getWritableDatabase().delete(TABLE_HOT_LINE, null, null);
    }

    //FAQ>------------------------------------------------------------------------------------------

    public Cursor getDataFaq() {
        return getReadableDatabase().query(TABLE_FAQ,
                null, null, null,
                null, null, null);
    }
    public void insertFAQ(Istories istories) {
        ContentValues values = new ContentValues();

        values.put(FAQ_QUESTION_COLUMN, istories.getNickName());
        values.put(FAQ_ANSWER_COLUMN,istories.getText());

        getWritableDatabase().insert(TABLE_FAQ, null, values);

    }
    public void deleteFAQ() {
        getWritableDatabase().delete(TABLE_FAQ, null, null);
    }

    //FORUM>----------------------------------------------------------------------------------------



    public Cursor getDataForum() {
        return getReadableDatabase().query(TABLE_FORUM,
                null, null, null,
                null, null, null);
    }
    public void insertForum(VseAndUzery vseAndUzery) {
        ContentValues values = new ContentValues();

        values.put(FORUM_TITLE_COLUMN, vseAndUzery.getTitle());
        values.put(FORUM_TEXT_COLUMN,vseAndUzery.getText());
        values.put(FORUM_USERNAME_COLUMN,vseAndUzery.getUsername());

        getWritableDatabase().insert(TABLE_FORUM, null, values);

    }
    public void deleteForum() {
        getWritableDatabase().delete(TABLE_FORUM, null, null);
    }


    //QA>--------------------------------------------------------------------------------------------
    public Cursor getDataQAById() {
        return getReadableDatabase().query(TABLE_QUESTION_ANSWER, null,null, null, null, null, null);
    }

    public Cursor getDataQA() {
        return getReadableDatabase().query(TABLE_QUESTION_ANSWER,
                null, null, null,
                null, null, null);
    }
    public void insertQA(VseAndUzery vseAndUzery) {
        ContentValues values = new ContentValues();

        values.put(QA_ID_COLUMN,vseAndUzery.getTitle());
        values.put(QA_QUESTION_COLUMN,vseAndUzery.getText());
        values.put(QA_USERNAME_COLUMN,vseAndUzery.getUsername());

        getWritableDatabase().insert(TABLE_QUESTION_ANSWER, null, values);

    }
    public void deleteQA() {
        getWritableDatabase().delete(TABLE_QUESTION_ANSWER, null, null);
    }


    //Answer>__________________________________________________________________________________


    public Cursor getDataAById(String id) {
        return getReadableDatabase().query(TABLE_ANSWER, null,A_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)}, null, null, null);
    }

    public Cursor getDataA() {
        return getReadableDatabase().query(TABLE_ANSWER,
                null, null, null,
                null, null, null);
    }
    public void insertA(VseAndUzery vseAndUzery,String id) {
        ContentValues values = new ContentValues();

        values.put(A_ID_COLUMN,id);
        values.put(A_ANSWER_COLUMN,vseAndUzery.getText());
        values.put(A_USERNAME_COLUMN,vseAndUzery.getUsername());

        getWritableDatabase().insert(TABLE_ANSWER, null, values);

    }
    public void deleteA() {
        getWritableDatabase().delete(TABLE_ANSWER, null, null);
    }
    public void deleteAById(String id){
        getWritableDatabase().delete(TABLE_ANSWER,A_ID_COLUMN + " = " + id, null);
    }





    //CONSULATE>------------------------------------------------------------------------------------

    public Cursor getDataConsulate(String id) {
        return getReadableDatabase().query(TABLE_CONSULATE,
                null, CONSULATE_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)},
                null, null, null);
    }
    public void insertConsulate(Consulate consulate,String id) {
        ContentValues values = new ContentValues();

        values.put(CONSULATE_ADDRESS_COLUMN, consulate.getAddress());
        values.put(CONSULATE_PHONE_COLUMN,consulate.getPhoneNumber());
        values.put(CONSULATE_PARENT_ID_COLUMN,id);
        values.put(CONSULATE_REGION_COLUMN,consulate.getRegion());

        getWritableDatabase().insert(TABLE_CONSULATE, null, values);

    }
    public void deleteConsulate() {
        getWritableDatabase().delete(TABLE_CONSULATE, null, null);
    }

//SIZE?>---------------------------------------------------------
public Cursor getDataSize() {
    return getReadableDatabase().query(TABLE_SIZE,
            null, null,null,
            null, null, null);
}
    public void insertSize(int x,int y) {
        ContentValues values = new ContentValues();

        values.put(SIZE_HEIGHT_COLUMN, x);
        values.put(SIZE_WIDTH_COLUMN,y);


        getWritableDatabase().insert(TABLE_SIZE, null, values);

    }
    public void deleteSize() {
        getWritableDatabase().delete(TABLE_SIZE, null, null);
    }



    public void deleteDias() {
        getWritableDatabase().delete(TABLE_DIAS, null, null);
    }

    public void deleteEAEU() {
        getWritableDatabase().delete(TABLE_EAEU, null, null);
    }
    public void deleteAbroad() {
        getWritableDatabase().delete(TABLE_ABROAD, null, null);
    }
    public void deleteEmploy() {
        getWritableDatabase().delete(TABLE_EMPLOY, null, null);
    }
    public void deleteEmployment() {
        getWritableDatabase().delete(TABLE_EMPLOYMENT, null, null);
    }
    public Cursor getEAEU(){
        return getReadableDatabase().query(TABLE_EAEU,null,null,null,null,null,null);
    }

    public void insertDias(EAEU eaeu) {
        ContentValues values = new ContentValues();

        values.put(DIAS_PICTURE_COLUMN,eaeu.getPicture() );
        values.put(DIAS_TITLE_COLUMN,eaeu.getName() );
        values.put(DIAS_ID_COLUMN,eaeu.getId() );



        getWritableDatabase().insert(TABLE_DIAS, null, values);
    }
    public Cursor getDias(){
        return getReadableDatabase().query(TABLE_DIAS,null,null,null,null,null,null);
    }

    public void insertEAEU(EAEU eaeu) {
        ContentValues values = new ContentValues();

        values.put(EAEU_PICTURE_COLUMN,eaeu.getPicture() );
        values.put(EAEU_ID_COLUMN,eaeu.getId() );
        values.put(EAEU_TITLE_COLUMN,eaeu.getName() );


        getWritableDatabase().insert(TABLE_EAEU, null, values);
    }
    public Cursor getEmploy(){
        return getReadableDatabase().query(TABLE_EMPLOY,null,null,null,null,null,null);
    }
    public void insertEmploy(EAEU eaeu) {
        ContentValues values = new ContentValues();

        values.put(EMPLOY_PICTURE_COLUMN,eaeu.getPicture() );
        values.put(EMPLOY_ID_COLUMN,eaeu.getId() );
        values.put(EMPLOY_TITLE_COLUMN,eaeu.getName() );


        getWritableDatabase().insert(TABLE_EMPLOY, null, values);
    }
    public void insertAbroad(EAEU eaeu) {
        ContentValues values = new ContentValues();

        values.put(ABROAD_PICTURE_COLUMN,eaeu.getPicture() );
        values.put(ABROAD_ID_COLUMN,eaeu.getId() );
        values.put(ABROAD_TITLE_COLUMN,eaeu.getName() );


        getWritableDatabase().insert(TABLE_ABROAD, null, values);
    }
    public Cursor getAbroad(){
        return getReadableDatabase().query(TABLE_ABROAD,null,null,null,null,null,null);
    }

    public Cursor getDataEmployment(String id) {
        return getReadableDatabase().query(TABLE_EMPLOYMENT,null,EMPLOYMENT_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)},null,null,null);
    }
    public void insertEmployment(Employment employment,String id) {
        ContentValues values = new ContentValues();

        values.put(EMPLOYMENT_NUMBER_COLUMN,employment.getPhone_number() );
        values.put(EMPLOYMENT_NUMBER2_COLUMN,employment.getPhone_number2() );
        values.put(EMPLOYMENT_NUMBER1_COLUMN,employment.getPhone_number1() );
        values.put(EMPLOYMENT_MANAGER_COLUMN,employment.getManager() );
        values.put(EMPLOYMENT_NAME_COLUMN,employment.getName() );
        values.put(EMPLOYMENT_PARENT_ID_COLUMN,id );

        values.put(EMPLOYMENT_ADDRESS_COLUMN,employment.getAdress() );





        getWritableDatabase().insert(TABLE_EMPLOYMENT, null, values);
    }

//HOT_LINE_COUNTRY>------------------------------------------------------------
public void insertHotCountry(EAEU eaeu) {
    ContentValues values = new ContentValues();

    values.put(HOT_COUNTRY_PICTURE_COLUMN,eaeu.getPicture() );
    values.put(HOT_COUNTRY_ID_COLUMN,eaeu.getId() );
    values.put(HOT_COUNTRY_TITLE_COLUMN,eaeu.getName() );


    getWritableDatabase().insert(TABLE_HOT_COUNTRY, null, values);
}
    public Cursor getHotCountry(){
        return getReadableDatabase().query(TABLE_HOT_COUNTRY,null,null,null,null,null,null);
    }

    public void deleteHotCountry() {
        getWritableDatabase().delete(TABLE_HOT_COUNTRY, null, null);
    }

    //NKOCOUNTRY>----------------------------------------------------------------

    public void insertNKOCountry(EAEU eaeu) {
        ContentValues values = new ContentValues();

        values.put(NKOC_PICTURE_COLUMN,eaeu.getPicture() );
        values.put(NKOC_ID_COLUMN,eaeu.getId() );
        values.put(NKOC_TITLE_COLUMN,eaeu.getName() );


        getWritableDatabase().insert(TABLE_NKOC, null, values);
    }
    public Cursor getNKOCountry(){
        return getReadableDatabase().query(TABLE_NKOC,null,null,null,null,null,null);
    }

    public void deleteNKOCountry() {
        getWritableDatabase().delete(TABLE_NKOC, null, null);
    }

    //NKO>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public Cursor getDataNKO(String id) {
        return getReadableDatabase().query(TABLE_NKO,
                null, NKO_PARENT_ID_COLUMN+ " = ? ", new String[]{String.valueOf(id)},
                null, null, null);
    }
    public void insertNKO(NKO consulate, String id) {
        ContentValues values = new ContentValues();

        values.put(NKO_ADDRESS_COLUMN, consulate.getAddress());
        values.put(NKO_MAIL_COLUMN,consulate.getMail());
        values.put(NKO_MANAGER_COLUMN,consulate.getManager());
        values.put(NKO_PHONE1_COLUMN,consulate.getPhone1());
        values.put(NKO_PHONE_COLUMN,consulate.getPhone());
        values.put(NKO_TITLE_COLUMN,consulate.getTitle());
        values.put(NKO_TEXT_COLUMN,consulate.getText());
        values.put(NKO_PARENT_ID_COLUMN,id);

        getWritableDatabase().insert(TABLE_NKO, null, values);

    }
    public void deleteNKO() {
        getWritableDatabase().delete(TABLE_NKO, null, null);
    }


}
