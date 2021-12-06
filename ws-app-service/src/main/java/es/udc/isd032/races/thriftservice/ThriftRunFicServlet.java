package es.udc.isd032.races.thriftservice;

import es.udc.isd032.races.thrift.ThriftRunFicService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

public class ThriftRunFicServlet extends TServlet  {

    public ThriftRunFicServlet() {
        super(createProcessor(), createProtocolFactory());
    }

    private static TProcessor createProcessor() {

        return new ThriftRunFicService.Processor<ThriftRunFicService.Iface>(
                new ThriftRunFicServiceImpl());

    }


    private static TProtocolFactory createProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

}
