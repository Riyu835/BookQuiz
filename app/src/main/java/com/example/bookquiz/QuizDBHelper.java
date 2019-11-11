package com.example.bookquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import com.example.bookquiz.QuizContract.QuestionsTable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "quizzes.db";
    private static final int DB_VERSION = 8;

    public static final String CATEGORY_MTK = "comic";
    public static final String CATEGORY_IPA = "indonesia";
    public static final String CATEGORY_IPS = "japan";
    public static final String CATEGORY_AGAMA = "online";
    public static final String CATEGORY_OLAHRAGA = "korea";
    public static final String CATEGORY_PROGRAM = "english";

    private final String CREATE_TABLE_QUERY = "CREATE TABLE " + QuizContract.QuestionsTable.TABLE_NAME +
            "(" +
            QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionsTable.COLUMN_QUESTION + " TEXT, " +
            QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER + " TEXT, " +
            QuestionsTable.COLUMN_CATEGORY + " TEXT" +
            ")";

    private final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME;

    private SQLiteDatabase db;
    private List<Question> mQuestionList;

    private Bundle categoryIntentBundle;

    public QuizDBHelper(Context context, Bundle bundle) {
        super(context, DB_NAME, null, DB_VERSION);
        this.categoryIntentBundle = bundle;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE_QUERY);

        setUpQuestions();
        insertQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    private void setUpQuestions() {
        mQuestionList = new ArrayList<>();

        //questions for category online / agama
        mQuestionList.add(new Question("\"Kemenangan bukan hanya tentang berhasil mengalahkan musuhmu, terkadang kemenangan adalah ttg melindungi orang lain\" adalah ucapan dari?", "Gerantang, Mantradeva ", "Selena, Selena The Moon Bride", "Val, Aegis Orta", "Envy, Deadly Seven Inside Me", "Gerantang, Mantradeva", CATEGORY_AGAMA));
        mQuestionList.add(new Question("\"Tidak ada yang buruk di dunia ini, bahkan duri yang tumbuh pada mawar pun menghiasi dengan cantik\" adalah qoutes dari webtoon?", "Change", "Secret of Angel", "In A Dream", "Choco Latte", "In A Dream", CATEGORY_AGAMA));
        mQuestionList.add(new Question("Nama creator dari Webtoon 7 Wonders adalah", "Deruu RioTa", "lunaria_co", "Metalu", "blackcatshooter", "Metalu", CATEGORY_AGAMA));
        mQuestionList.add(new Question("\"Sekuat apapun perempuan itu, tetap saja jika hati mereka sedang terluka, mereka akan menangis\" adalah qoutes dari webtoon?", "Sweet EscapE", "Curious Date", "Only You Don't Know", "Girls of the Wild's", "Girls of the Wild's", CATEGORY_AGAMA));
        mQuestionList.add(new Question("Chairunnisa adalah creator dari Webtoon...", "Matahari 1/2 Lingkar", "Just Friends", "SEKOTENGS", "KOSAN 95!", "Matahari 1/2 Lingkar", CATEGORY_AGAMA));

        //questions for category indonesia / ipa
        mQuestionList.add(new Question("\"Jika hidup itu sekali, mati itu sekali, maka cinta juga sekali\" adalah kutipan dari..", "Rembulan Tenggelam di Wajahmu, Tere Liye", "Dilan 1990, Pidi Baiq", "Just Be Mine, Pit Sansi", "A, Wulanfadi", "Rembulan Tenggelam di Wajahmu, Tere Liye", CATEGORY_IPA));
        mQuestionList.add(new Question("Hiro Morrison adalah nama tokoh utama dari buku?", "Mantan, Siti Umrotun", "Lavina, Ainun Nufus", "Story of Seth, Wulanfadi", "Touche: Alchemist, Windhy Puspitadewi", "Touche: Alchemist, Windhy Puspitadewi", CATEGORY_IPA));
        mQuestionList.add(new Question("\"Sahabat sejati itu, orang yang ngga pernah berhenti percaya sama sahabatnya sendiri, walau dia sudah ngga percaya lagi sama kita.\" adalah quote dari novel?", "Harga Sebuah Percaya, Tere Liye", "Dan Hujan Pun Berhenti, Farida Susanty", "Kata, Rintik Sedu", "Nanti Kita Cerita Tentang Hari Ini, Marchella FP", "Dan Hujan Pun Berhenti, Farida Susanty", CATEGORY_IPA));
        mQuestionList.add(new Question("Kakak dari Si Babi Hutan di serial buku \"Pulang\" adalah...", "Agam", "Basyir", "Samad", "Diego", "Diego", CATEGORY_IPA));
        mQuestionList.add(new Question("\"Aku boleh jadi tipikal orang yang tidak kau sukai, menyebalkan. Tapi aku selalu memegang janjiku. Kau akan mendengar semuanya. Terserah kau mau menulis apa setelah itu, dunia ini jelas tidak hitam-putih!\" adalah quote dari buku?", "Negeri Para Bedebah, Tere Liye", "Bumi Manusia, Pramoedya Ananta Toer", "Aroma Karsa, Dee Lestari", "Mariposa, Luluk HF", "Negeri Para Bedebah, Tere Liye", CATEGORY_IPA));

        //questions for category japan / ips
        mQuestionList.add(new Question("Judul novel keempat dari serial \"Hyouka\" karya Yonezawa Honobu", "The Doll That Took a Detour", "The Kudryavka Sequence", "Credit Roll of the Fool", "Hyouka", "The Doll That Took a Detour", CATEGORY_IPS));
        mQuestionList.add(new Question("Buku \"Rashoumon\" adalah karya dari... ", "Nakahara Chuuya", "Nakajima Atsushi", "Osamu Dazai", "Akutagawa Ryuunosuke", "Akutagawa Ryuunosuke", CATEGORY_IPS));
        mQuestionList.add(new Question("Novel karya Imamura Masahiro yang diterjemahkan oleh Penerbit Haru adalah...", "The Dead Returns", "Murder at Shijinso", "Scheduled Suicide Day", "Girls in the Dark", "Murder at Shijinso", CATEGORY_IPS));
        mQuestionList.add(new Question("Tokoh utama dalam novel karya Minato Kanae yang berjudul \"Confessions\" adalah...", "Chitanda Eru", "Shiraishi Itsumi", "Moriguchi Yuko", "Oreki Hotaro", "Moriguchi Yuko", CATEGORY_IPS));
        mQuestionList.add(new Question("Buku karya Sumino Yoru yang pernah diterbitkan oleh Penerbit Haru adalah...", "Yawning is Delicious", "Another", "I Saw the Same Dream Again", "Holy Mother", "I Saw the Same Dream Again", CATEGORY_IPS));

        //questions for category comic / mtk
        mQuestionList.add(new Question("\"Satu senyuman yang tulus lebih bermakna daripada seribu kata yang diucapkan dengan setengah hati,\" adalah perkataan dari...", "Kagome, Inuyasha", "Sebastian Michaelis, Black Butler", "Naruto Uzumaki, Naruto Shippuden", "Dazai Osamu, Bungou Stray Dogs", "Kagome, Inuyasha", CATEGORY_MTK));
        mQuestionList.add(new Question("Jurus Rashoumon yang ada di Manga Naruto Shippuden, diambil dari buku karya...", "Edogawa Ranpo", "Nakajima Atsushi", "Akutagawa Ryuunosuke", "Dazai Osamu", "Akutagawa Ryuunosuke", CATEGORY_MTK));
        mQuestionList.add(new Question("Nama asli dari Kaito Kid di manga Detective Conan adalah...", "Conan Edogawa", "Kudo Shinichi", "Hatori Heiji", "Kaito Kuroba", "Kaito Kuroba", CATEGORY_MTK));
        mQuestionList.add(new Question("\"Aku tidak malas, aku hanya menyimpan energi,\" adalah moto hidup dari...", "Inuyasha, Inuyasha", "Oreki Hotaro, Hyouka", "Kaneki Ken, Tokyo Ghoul", "Bruno, Oushitsu Kyoushi Haine", "Oreki Hotaro, Hyouka", CATEGORY_MTK));
        mQuestionList.add(new Question("Anggota dari kelompok 7 dalam serial Boruto: Naruto Next Generation adalah...", "Boruto, Sarada, Mitsuki", "Shikadai, Inojin, Chocho", "Naruto, Sakura, Sasuke", "Shikamaru, Ino, Choji", "Boruto, Sarada, Mitsuki", CATEGORY_MTK));

        //questions for category korea / olahraga
        mQuestionList.add(new Question("Buku \"Love Alert\" karya Seo Han Gyeoul merupakan terbitan dari...", "Penerbit Haru", "Penerbit Inari", "Penerbit Spring", "Penerbit Koru", "Penerbit Haru", CATEGORY_OLAHRAGA));
        mQuestionList.add(new Question("Buku yang pernah direkomendasikan Hwang Min Hyun di broadcasts V-Live dan diterbitkan oleh Penerbit Haru berjudul...", "A Piece of Moon", "The Secret of Red Sky", "So I Married the Anti-fan", "Last Love Story of Walden Brothers", "A Piece of Moon", CATEGORY_OLAHRAGA));
        mQuestionList.add(new Question("\"Switched Dating\" adalah buku terjemahan korea dari Penerbit Haru, karya...", "Jung Eun Gwol", "Jeong Gyeong Yun", "Kim Eun Jeong", "Kang Young Joo", "Kang Young Joo", CATEGORY_OLAHRAGA));
        mQuestionList.add(new Question("Buku dengan tema vampir karya Shin Ji Eun yang diterjemahkan oleh Penerbit Haru berjudul...", "Vampire Flower", "The Secret of Red Sky", "The Last 2%", "Moon in the Spring", "Vampire Flower", CATEGORY_OLAHRAGA));
        mQuestionList.add(new Question("Judul buku karya Kang Ji Young yang bertema tentang kisah wanita yang bekerja sebagai pembersih lokasi pembunuhan, dan wanita cantik kaya raya yang bertukar tubuh di dalam mimpi adalah...", "Cheeky Romance", "Wishing Her to Die", "Yawning is Delicious", "Angels of Morning Star Club", "Yawning is Delicious", CATEGORY_OLAHRAGA));

        //questions for category english / program
        mQuestionList.add(new Question("Buku keempat dari serial novel Divergent adalah...", "Insurgent", "Allegiant", "Divergent", "Four", "Four", CATEGORY_PROGRAM));
        mQuestionList.add(new Question("Judul Serial Prequel kedua dari The Mortal Instrument adalah...", "The Dark Artifices", "The Infernal Devices", "The Last Hours", "The Wicked Powers", "The Last Hours", CATEGORY_PROGRAM));
        mQuestionList.add(new Question("Monster High/Ever After High The Legend of Shadow High, adalah buku karangan?", "Shannon Hale dan Suzanne Selfors", "Dean Hale dan Max Dale", "Max Dale dan Shannon Hale", "Shannon Hale dan Dean Hale", "Shannon Hale dan Dean Hale", CATEGORY_PROGRAM));
        mQuestionList.add(new Question("Penulis dari serial Ever After High A School Story adalah...", "Shannon Hale", "Suzzane Selfors", "Cassandra Clare", "Melissa De La Cruz", "Suzzane Selfors", CATEGORY_PROGRAM));
        mQuestionList.add(new Question("Buku yang diadaptasikan menjadi film musikal Disney Descendants berjudul... karangan...", "Isle of The Lost, Melissa de la Cruz", "Ever After High The Storybook of Legends, Shannon Hale", "The School for Good and Evil, Soman Chainani", "The Land of Stories The Wishing Spell, Chris Colfer", "Isle of The Lost, Melissa de la Cruz", CATEGORY_PROGRAM));
    }


    private void insertQuestions() {
        for(Question q : mQuestionList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(QuestionsTable.COLUMN_QUESTION, q.getmQuestion());
            contentValues.put(QuestionsTable.COLUMN_OPTION1, q.getmOption1());
            contentValues.put(QuestionsTable.COLUMN_OPTION2, q.getmOption2());
            contentValues.put(QuestionsTable.COLUMN_OPTION3, q.getmOption3());
            contentValues.put(QuestionsTable.COLUMN_OPTION4, q.getmOption4());
            contentValues.put(QuestionsTable.COLUMN_ANSWER, q.getmAnswer());
            contentValues.put(QuestionsTable.COLUMN_CATEGORY, q.getmCategory());
            db.insert(QuestionsTable.TABLE_NAME, null, contentValues);
        }
    }

    public ArrayList<Question> getAllQuestions(String categoryID) {
        Log.d("TAG", "Getting all questions for : " + categoryID);
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String SELECT_TABLE_QUERY = "SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_CATEGORY + " = \"" + categoryID + "\"";
        Cursor cursor = db.rawQuery(SELECT_TABLE_QUERY, null);
        if(cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setmQuestion(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setmOption1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setmOption2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setmOption3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setmOption4(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setmAnswer(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_ANSWER)));
                question.setmCategory(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}