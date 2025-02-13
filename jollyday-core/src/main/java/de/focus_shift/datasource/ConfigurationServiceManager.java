package de.focus_shift.datasource;

import de.focus_shift.ManagerParameter;
import de.focus_shift.spi.ConfigurationService;
import de.focus_shift.util.ClassLoadingUtil;

import static de.focus_shift.ManagerParameter.CONFIGURATION_DATASOURCE_IMPL_CLASS;

/**
 * This manager is responsible for instantiating the configured configuration datasource
 * which is used to access the holiday data.
 */
public class ConfigurationServiceManager {

  private final ClassLoadingUtil classLoadingUtil = new ClassLoadingUtil();

  public ConfigurationService getConfigurationService(ManagerParameter parameter) {
    validateConfiguration(parameter);
    final String dataSourceClassName = parameter.getProperty(CONFIGURATION_DATASOURCE_IMPL_CLASS);
    return instantiateDataSource(dataSourceClassName);
  }

  private ConfigurationService instantiateDataSource(String dataSourceClassName) {
    try {
      final Class<?> dataSourceClass = classLoadingUtil.loadClass(dataSourceClassName);
      return (ConfigurationService) dataSourceClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new IllegalStateException("Cannot instantiate datasource instance of " + dataSourceClassName, e);
    }
  }

  private void validateConfiguration(ManagerParameter parameter) {
    if (parameter.getProperty(CONFIGURATION_DATASOURCE_IMPL_CLASS) == null) {
      throw new IllegalStateException("Missing holiday configuration datasource implementation class under config key " + CONFIGURATION_DATASOURCE_IMPL_CLASS);
    }
  }
}
