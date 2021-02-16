package theMarked.characters;

import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Player;

public class Listener implements Player.PlayerListener {
    private TheMarked character;
    public Listener(TheMarked character) {
        this.character = character;
    }
    public void animationFinished(Animation animation){
        character.resetAnimation();
    }

    public void animationChanged(Animation var1, Animation var2){ }
    public void preProcess(Player var1){ }
    public void postProcess(Player var1){ }
    public void mainlineKeyChanged(com.brashmonkey.spriter.Mainline.Key var1, com.brashmonkey.spriter.Mainline.Key var2){ }
}