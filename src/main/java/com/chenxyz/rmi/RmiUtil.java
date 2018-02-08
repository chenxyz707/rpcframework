package com.chenxyz.rmi;

import com.chenxyz.loadbalance.NodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public class RmiUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param host
     * @param port
     * @param id
     */
    public void startRmiServer(String host, String port, String id) {
        try {
            SoaRmi soaRmi = new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.valueOf(port));
            //rmi://127.0.0.1:1135/query
            Naming.bind("rmi://" + host + ":" + port + "/" + id, soaRmi);
            logger.info("rmi server start!!!");
        } catch (RemoteException e) {
            logger.error("error start rmi server", e);
        } catch (MalformedURLException e) {
            logger.error("error start rmi server", e);
        } catch (AlreadyBoundException e) {
            logger.error("error start rmi server", e);
        }

    }

    public SoaRmi startRmiClient(NodeInfo nodeInfo, String id) {
        String host = nodeInfo.getHost();
        String port = nodeInfo.getPort();
        try {
            return (SoaRmi)Naming.lookup("rmi://" + host + ":" + port + "/" + id);
        } catch (NotBoundException e) {
            logger.error("error start rmi client", e);
        } catch (MalformedURLException e) {
            logger.error("error start rmi client", e);
        } catch (RemoteException e) {
            logger.error("error start rmi client", e);
        }
        return null;
    }
}
