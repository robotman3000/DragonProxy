/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 *                       Version 3, 29 June 2007
 *
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 *
 * You can view LICENCE file for details. 
 *
 * @author The Dragonet Team
 */
package org.dragonet.proxy.network.adapter;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.marfgamer.jraknet.RakNetException;
import net.marfgamer.jraknet.RakNetPacket;
import net.marfgamer.jraknet.client.RakNetClient;
import net.marfgamer.jraknet.client.RakNetClientListener;
import net.marfgamer.jraknet.client.Warning;
import net.marfgamer.jraknet.identifier.Identifier;
import net.marfgamer.jraknet.protocol.Reliability;
import net.marfgamer.jraknet.protocol.message.acknowledge.Record;
import net.marfgamer.jraknet.session.RakNetServerSession;
import org.dragonet.proxy.DragonProxy;
import org.dragonet.proxy.network.ClientConnection;
import org.dragonet.proxy.network.ConnectionStatus;
import org.dragonet.proxy.network.PacketTranslatorRegister;

/**
 * @author robotman3000
 */
public class MCPEServerProtocolAdapter implements ServerProtocolAdapter<RakNetPacket>, RakNetClientListener {

    /**
     * The PE Server
     */
    private RakNetClient client;

    @Getter
    /**
     * The PE Client
     */
    private ClientConnection upstream;
    private List<RakNetPacket> queuedPackets = new ArrayList<>();

    public MCPEServerProtocolAdapter() {
        client = new RakNetClient();
        client.setListener(this);
    }

    @Override
    public void connectToRemoteServer(String address, int port) {
        DragonProxy.getLogger().info("[" + upstream.getUsername() + "] Connecting to remote pocket server at [" + String.format("%s:%s", address, port) + "] ");
        try {
            client.connectThreaded(address, port);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            //Logger.getLogger(MCPEServerProtocolAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Logger.getLogger(MCPEServerProtocolAdapter.class.getName()).log(Level.SEVERE, null, ex);

    }

    @Override
    public void disconnectFromRemoteServer(String reason) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setClient(ClientConnection session) {
        this.upstream = session;
    }

    @Override
    public void sendPacket(RakNetPacket packet) {
        DragonProxy.getLogger().debug("[PE Serverside] Sending Packet: " + upstream.getSessionID() + ": " + Integer.toHexString(packet.buffer().getByte(1)));
        if (client.isConnected()) {
            client.sendMessage(Reliability.RELIABLE, packet);
        } else {
            System.out.println("Queuing packet");
            queuedPackets.add(packet);
        }
    }

    @Override
    public void onAcknowledge(RakNetServerSession session, Record record, Reliability reliability, int channel, RakNetPacket packet) {
    }

    @Override
    public void onConnect(RakNetServerSession session) {
        DragonProxy.getLogger().info("Remote pocket server downstream established!");
        
        for (RakNetPacket pk : queuedPackets) {
            System.out.println("Handling queued packet: " + pk.getId());
            sendPacket(pk);
        }
    }

    @Override
    public void onDisconnect(RakNetServerSession session, String reason) {
        DragonProxy.getLogger().info("Remote pocket server downstream CLOSED!\nReason: " + reason);
    }

    @Override
    public void onExternalServerAdded(InetSocketAddress address) {
    }

    @Override
    public void onExternalServerIdentifierUpdate(InetSocketAddress address, Identifier identifier) {
    }

    @Override
    public void onExternalServerRemoved(InetSocketAddress address) {
    }

    @Override
    public void onHandlerException(InetSocketAddress address, Throwable throwable) {
        DragonProxy.getLogger().severe("An unhandled exception has occured with the server session: " + address);
        throwable.printStackTrace();
    }

    @Override
    public void onNotAcknowledge(RakNetServerSession session, Record record, Reliability reliability, int channel, RakNetPacket packet) {
    }

    @Override
    public void onServerDiscovered(InetSocketAddress address, Identifier identifier) {
    }

    @Override
    public void onServerForgotten(InetSocketAddress address) {
    }

    @Override
    public void onServerIdentifierUpdate(InetSocketAddress address, Identifier identifier) {
    }

    @Override
    public void onThreadException(Throwable throwable) {
        DragonProxy.getLogger().severe("An unhandled thread exception has with the server occured");
        throwable.printStackTrace();
    }

    @Override
    public void onWarning(Warning warning) {
        DragonProxy.getLogger().warning("Proxy Warn: " + warning.getMessage());
    }

    @Override
    public void handlePacket(RakNetServerSession session, RakNetPacket packet, int channel) {
        handlePacket(packet, upstream);
    }

    @Override
    public void handlePacket(RakNetPacket packet, ClientConnection session) {
        DragonProxy.getLogger().debug("[PE Serverside] Handling Packet: " + session.getSessionID() + ": " + Integer.toHexString(packet.buffer().getByte(1)));

        Object[] packets = {new RakNetPacket(packet.buffer())};
        if (upstream.getUpstreamProtocol().getSupportedPacketType() != getSupportedPacketType()) {
            packets = PacketTranslatorRegister.translateToPC(upstream, packet);
        }

        for (Object pack : packets) {
            upstream.getUpstreamProtocol().sendPacket(pack, upstream);
        }
    }

    @Override
    public Class<RakNetPacket> getSupportedPacketType() {
        return RakNetPacket.class;
    }
}