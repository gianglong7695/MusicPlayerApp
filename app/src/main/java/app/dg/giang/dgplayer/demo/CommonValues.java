package app.dg.giang.dgplayer.demo;

/**
 * Created by TAKASHI20 on 8/12/2015.
 */
public class CommonValues {
    /**
     * For Listener Music
     */

    // Intent action
    public static final String ACTION_TOGGLE_PLAYBACK = "app.dg.giang.dgplayer.TOGGLE_PLAYBACK";
    public static final String ACTION_PLAY = "app.dg.giang.dgplayer.PLAY";
    public static final String ACTION_PREVIOUS = "app.dg.giang.dgplayer.PREVIOUS";
    public static final String ACTION_NEXT = "app.dg.giang.dgplayer.NEXT";
    public static final String ACTION_PAUSE = "app.dg.giang.dgplayer.PAUSE";
    public static final String ACTION_SEEK = "app.dg.giang.dgplayer.SEEK";
    public static final String SUPPLY_PLAYLIST = "app.dg.giang.dgplayer.SUPPLY_PLAYLIST";
    public static final String ACTION_PLAY_SPECIFIC_SONG = "app.dg.giang.dgplayer.PLAY_SPECIFIC_SONG";
    public static final String ACTION_TOGGLE_REPEATMODE = "app.dg.giang.dgplayer.TOGGLE_REPEATMODE";
    public static final String ACTION_TOGGLE_SHUFFLE = "app.dg.giang.dgplayer.TOGGLE_SHUFFLE";
    public static final String REQUEST_STATUS = "app.dg.giang.dgplayer.REQUEST_STATUS";
    public static final String ACTION_REORDER_PLAYLIST = "app.dg.giang.dgplayer.REORDER_PLAYLIST";
    public static final String ACTION_SETUP_AS_FOREGROUND = "app.dg.giang.dgplayer.SETUP_AS_FOREGROUND";

    // Intent action notififcation
    public static final String ACTION_NEXT_NOTIFICATION = "app.dg.giang.dgplayer.NEXT_NOTIFICATION";
    public static final String ACTION_PLAY_NOTIFICATION = "app.dg.giang.dgplayer.PLAY_NOTIFICATION";
    public static final String ACTION_PREV_NOTIFICATION = "app.dg.giang.dgplayer.PREV_NOTIFICATION";
    public static final String ACTION_CLOSE_NOTIFICATION = "app.dg.giang.dgplayer.CLOSE_NOTIFICATION";
    public static final String ACTION_LIKE_NOTIFICATION = "app.dg.giang.dgplayer.LIKE_NOTIFICATION";
    public static final String ACTION_SHUFFLE_NOTIFICATION = "app.dg.giang.dgplayer.SHUFFLE_NOTIFICATION";
    public static final String ACTION_REPEAT_NOTIFICATION = "app.dg.giang.dgplayer.REPEAT_NOTIFICATION";


    // Bundle key string
    public static final String BKEY_STATE = "MEDIAPLAYER_STATE";
    public static final String BKEY_CURSONG_DURATION = "SONG_DURATION";
    public static final String BKEY_CURSONG_POSITION = "SONG_POSITION";
    public static final String BKEY_PERCENTAGE = "PERCENTAGE";
    public static final String BKEY_REPEATMODE = "REPEAT_MODE";
    public static final String BKEY_SHUFFLE = "SHUFFLE";
    // Broadcast status action
    public static final String STATUS_BC_NOW_PLAYING = "app.dg.giang.dgplayer.NOW_PLAYING";
    public static final String STATUS_BC_PLAYTIME = "app.dg.giang.dgplayer.PLAYTIME";
    public static final String STATUS_BC_ALL = "app.dg.giang.dgplayer.ALLSTATUS";
    public static final String STATUS_BC_PLAYMODE = "app.dg.giang.dgplayer.PLAYMODE";
    public static final String STATUS_BC_NOWPLAYING_PLAYLIST = "app.dg.giang.dgplayer.NOWPLAYING_PLAYLIST";


    public static final String MY_PREF = "PREF_MUSIC_ONLINE";
    public static final String CURRENT_FRAGMENT = "MO_CURRENT_FRAGMENT";

    public static final String SETTING_SHUFFLE = "SHUFFLE";
    public static final String SETTING_REPEAT = "REPEAT";

    public static final String POSITION_ITEM = "POSITION_ITEM";
    public static final String TYPE_ITEM = "TYPE_ITEM";
    public static final String FOLDER = "FOLDER";
    public static final String PLAYLIST = "PLAYLIST";
    public static final String FAVORITES = "FAVORITES";
    public static final String ARTIST = "ARTIST";
    public static final String ALBUM = "ALBUM";
    public static boolean isNeedAddView = false;
    public static boolean isMute = true;
    public static int currentVolume = 0;

    /*--Settings App--*/
    public static final String ENABLE_SHAKE_DEVICES = "ENABLE_SHAKE_DEVICES.";
    public static final String SHAKE_THRESHOLD_GRAVITY = "SHAKE_THRESHOLD_GRAVITY";
    public static final String SHAKE_SLOP_TIMEMS = "SHAKE_SLOP_TIMEMS";
    /*----Settings Equalizer--*/
    public static final String EQ_ENABLE = "EQ_ENABLE";
    public static final String EQ_COLUMN0 = "EQ_COLUMN0";
    public static final String EQ_COLUMN1 = "EQ_COLUMN1";
    public static final String EQ_COLUMN2 = "EQ_COLUMN2";
    public static final String EQ_COLUMN3 = "EQ_COLUMN3";
    public static final String EQ_COLUMN4 = "EQ_COLUMN4";
    public static final String EQ_BASS_BOOST = "EQ_BASS_BOOST";


}
