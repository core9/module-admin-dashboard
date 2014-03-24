package io.core9.plugin.admin.dashboard;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.features.FeaturesProvider;
import io.core9.plugin.server.VirtualHost;

import java.util.Map;

public interface AdminDashboardPlugin extends Core9Plugin, FeaturesProvider {

	/**
	 * Add a dashboard plugin
	 * @param filepath
	 * @param modulename
	 */
	void addDashboardPlugin(VirtualHost vhost, String filepath, String modulename);

	/**
	 * Remove a dashboard plugin
	 * @param vhost
	 * @param string
	 */
	void removeDashboardPlugin(VirtualHost vhost, String string);
	
	/**
	 * Return the dashboard configuration
	 * @param vhost
	 * @return
	 */
	Map<String,Object> getDashboardConfig(VirtualHost vhost);

}
