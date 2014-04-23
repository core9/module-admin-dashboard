package io.core9.plugin.admin.dashboard;

import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerDefaultConfig;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
				@SuppressWarnings("unchecked")
				Set<String> set = new LinkedHashSet<String>((Collection<? extends String>) dashboardConf.get("modules"));
				result.put("jsfiles", set);
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
