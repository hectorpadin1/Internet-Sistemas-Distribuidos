package es.udc.isd032.races.model.race;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

/**
 * A factory to get
 * <code>SqlRaceDao</code> objects. <p> Required configuration parameters: <ul>
 * <li><code>SqlRaceDaoFactory.className</code>: it must specify the full class
 * name of the class implementing
 * <code>SqlRaceDao</code>.</li> </ul>
 */

public class SqlRaceDaoFactory {
    private final static String CLASS_NAME_PARAMETER = "SqlRaceDaoFactory.className";
    private static SqlRaceDao dao = null;

    private SqlRaceDaoFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static SqlRaceDao getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager.getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlRaceDao) daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static SqlRaceDao getDao() {
        if (dao == null) {
            dao = getInstance();
        }
        return dao;
    }
}
