package inf112.skeleton.app.network;

import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;

public class ClassRegister {
    public static Class[] classes = {
            PacketAddPlayer.class,
            NetworkPlayer.class,
            PacketNewConnectionResponse.class,
            PacketUpdatePosition.class,
            PacketRemovePlayer.class
    };
}
