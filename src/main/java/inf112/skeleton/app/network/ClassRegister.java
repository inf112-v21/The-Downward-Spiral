package inf112.skeleton.app.network;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketKeepAlive;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;
import inf112.skeleton.app.network.packets.PacketRemovePlayer;
import inf112.skeleton.app.network.packets.PacketRequestHand;
import inf112.skeleton.app.network.packets.PacketRespondHand;
import inf112.skeleton.app.network.packets.PacketExecuteCard;

import java.util.ArrayList;

public class ClassRegister {
    public static Class[] classes = {
            PacketAddPlayer.class,
            NetworkPlayer.class,
            PacketNewConnectionResponse.class,
            PacketUpdatePosition.class,
            PacketRemovePlayer.class,
            Direction.class,
            ArrayList.class,
            Card.class,
            com.badlogic.gdx.utils.Array.class,
            PacketRequestHand.class,
            PacketRespondHand.class,
            PacketExecuteCard.class,
            PacketKeepAlive.class
    };
}
