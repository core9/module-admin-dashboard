package io.core9.plugin.admin.dashboard;

import io.core9.plugin.server.VirtualHost;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class AdminDashboardFeaturesProcessorImpl implements AdminDashboardFeaturesProcessor {
	
	@InjectPlugin
	private AdminDashboardPlugin dashboard;

	@Override
	public String getFeatureNamespace() {
		return "adminplugin";
	}

	@Override
	public String getProcessorAdminTemplateName() {
		return "featureprocessor/adminplugin.tpl.html";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleFeature(VirtualHost vhost, File repository, Map<String, Object> item) {
		dashboard.addDashboardPlugin(vhost, (String) item.get("file"), (List<String>) item.get("dependencies"));
	}

	@Override
	public void removeFeature(VirtualHost vhost, File repository, Map<String, Object> item) {
		dashboard.removeDashboardPlugin(vhost, (String) item.get("file"));
	}

	@Override
	public boolean updateFeatureContent(VirtualHost vhost, File repository, Map<String, Object> item) {
		return true;
	}

}
