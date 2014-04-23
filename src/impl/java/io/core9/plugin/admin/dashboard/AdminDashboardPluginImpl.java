package io.core9.plugin.admin.dashboard;

import io.core9.plugin.admin.plugins.AdminConfigRepository;
import io.core9.plugin.server.VirtualHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class AdminDashboardPluginImpl implements AdminDashboardPlugin {
	
	private static final String CONFIG_MODULES_NAME = "modules";

	@InjectPlugin
	private AdminConfigRepository configRepository;

	@Override
	@SuppressWarnings("unchecked")
	public void removeDashboardPlugin(VirtualHost vhost, String file) {
		Map<String,Object> modulesConf = getDashboardConfig(vhost);
		List<Map<String,Object>> modules = (List<Map<String,Object>>) modulesConf.get(CONFIG_MODULES_NAME);
		for(Iterator<Map<String,Object>> it = modules.iterator(); it.hasNext();) {
			Map<String,Object> module = it.next();
			if(module.get("file").equals(file)) {
				it.remove();
			}
		}
		configRepository.updateConfig(vhost, "dashboard", (String) modulesConf.get("_id"), modulesConf);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addDashboardPlugin(VirtualHost vhost, String filepath, List<String> dependencies) {
		Map<String,Object> modulesConf = getDashboardConfig(vhost);
		List<Map<String,Object>> modules = (List<Map<String,Object>>) modulesConf.get(CONFIG_MODULES_NAME);
		Map<String,Object> module = new HashMap<String,Object>();
		module.put("file", filepath);
		module.put("dependencies", dependencies);
		modules.add(module);
		configRepository.updateConfig(vhost, "dashboard", (String) modulesConf.get("_id"), modulesConf);
	}
	
	@Override
	public Map<String,Object> getDashboardConfig(VirtualHost vhost) {
		List<Map<String,Object>> configs = configRepository.getConfigList(vhost, "dashboard");
		for(Map<String,Object> config : configs) {
			if(config.get("type").equals(CONFIG_MODULES_NAME)) {
				return config;
			}
		}
		Map<String,Object> newConfig = createStandardModuleConfiguration();
		configRepository.createConfig(vhost, "dashboard", newConfig);
		return newConfig;
	}

	/**
	 * Create a standard configuration
	 * @return
	 */
	private Map<String,Object> createStandardModuleConfiguration() {
		Map<String,Object> newConfig = new HashMap<String,Object>();
		newConfig.put("type", CONFIG_MODULES_NAME);
		newConfig.put(CONFIG_MODULES_NAME, new ArrayList<Map<String,Object>>());
		return newConfig;
	}

	@Override
	public String getFeatureName() {
		return "core9/dashboard";
	}

	@Override
	public String getFeatureVersion() {
		return "1.0.0";
	}

	@Override
	public String getRepositoryPath() {
		return "https://github.com/core9/feature-admin-dashboard.git";
	}
}
