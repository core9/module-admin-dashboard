package io.core9.plugin.admin.dashboard;

import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerDefaultConfig;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			@SuppressWarnings("unchecked")
			public Map<String, Object> handle(Request req) {
				Map<String,Object> result = new HashMap<String,Object>();
				List<String> jsfiles = new ArrayList<String>();
				List<String> ngmods = new ArrayList<String>();
				Map<String,Object> dashboardConf = dashboard.getDashboardConfig(req.getVirtualHost());
				List<Map<String,String>> modules = (List<Map<String, String>>) dashboardConf.get("modules");
				for(Map<String,String> module : modules) {
					jsfiles.add(module.get("filepath"));
					ngmods.add(module.get("modulename"));
				}
				result.put("jsfiles", jsfiles);
				result.put("ngmods", ngmods);
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
