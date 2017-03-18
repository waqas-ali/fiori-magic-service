package user.oauth.app.helpers;

import java.util.List;

import user.oauth.jpa.model.AliasDomain;

public class StringHelper {
	
	public static String getDomain(String url) {
		String domain = "";
		int startIndex = url.indexOf('@') + 1;
		int endIndex = url.lastIndexOf('.');
		domain = url.substring(startIndex, endIndex);
		return domain;
	}
	
	public static String ArrayListToString(List<AliasDomain> list) {
		String data = "";
		for ( AliasDomain aliasName : list) {
			data += aliasName.getAliasName()+" , ";
		}
		data.substring(0,data.length()-1);
		return data;
	}
	
}
