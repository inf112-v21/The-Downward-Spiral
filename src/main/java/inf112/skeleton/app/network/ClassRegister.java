package inf112.skeleton.app.network;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.network.packets.*;

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
