package io.core9.plugin.admin.dashboard;

import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerDefaultConfig;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class DashboardPluginsDatahandlerImpl implements	DashboardPluginsDatahandler<DataHandlerDefaultConfig> {
	
	@InjectPlugin
	private AdminDashboardPlugin dashboard;

	@Override
	public DataHandler<DataHandlerDefaultConfig> createDataHandler(final DataHandlerFactoryConfig config) {
		return new DataHandler<DataHandlerDefaultConfig>(){

			@Override
			public DataHandlerDefaultConfig getOptions() {
				return (DataHandlerDefaultConfig) config;
			}

			@Override
			public Map<String, Object> handle(Request req) {
				Map<String,Object> result = new HashMap<String,Object>();
				Map<String,Object> dashboardConf = dashboard.getDashboardConfig(req.getVirtualHost());
				Set<String> jsPlugins = new LinkedHashSet<String>();
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> modules = ((List<Map<String,Object>>) dashboardConf.get("modules"));
				for(Map<String,Object> module : modules) {
					@SuppressWarnings("unchecked")
					List<String> dependencies = (List<String>) module.get("dependencies");
					if(dependencies != null) {
						for(String dependency : dependencies) {
							if(!jsPlugins.contains(dependency)) {
								jsPlugins.add(dependency);
							}
						}
					}
					jsPlugins.add((String) module.get("file"));
				}
				result.put("jsfiles", jsPlugins);
				return result;
			}
		};
	}

	@Override
	public Class<? extends DataHandlerFactoryConfig> getConfigClass() {
		return DataHandlerDefaultConfig.class;
	}

	@Override
	public String getName() {
		return "DashboardPlugins";
	}

}
