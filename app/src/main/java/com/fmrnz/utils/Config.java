package com.fmrnz.utils;

public class Config {
	// File upload url (replace the ip with your server address)
	//public static final String FILE_UPLOAD_URL = "http://webdev.myserviceaccess.com/nhp_dir/fileUpload.php";
//	public static final String FILE_UPLOAD_URL = "http://childlf.tigerdalal.com/Webapi/ChildDetails.aspx?Remarks=hello&lat=13&long=27.5&Device=abc";
	public static final String FILE_UPLOAD_URL = "http://childlf.tigerdalal.com/Webapi/ChildDetails.aspx?Remarks=hello&lat=13&long=27.5&Device=abc";

	// Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
	public static final String TOPIC_GLOBAL = "global";

	// broadcast receiver intent filters
	public static final String REGISTRATION_COMPLETE = "registrationComplete";
	public static final String PUSH_NOTIFICATION = "pushNotification";

	// id to handle the notification in the notification tray
	public static final int NOTIFICATION_ID = 100;
	public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

	public static final String SHARED_PREF = "ah_firebase";
}
