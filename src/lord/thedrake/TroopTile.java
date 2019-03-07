package lord.thedrake;

public final class TroopTile implements Tile {

    private final Troop troop;
    private final PlayingSide side;
    private final TroopFace face;
    public TroopTile(Troop troop, PlayingSide side, TroopFace face) {
        this.troop = troop;
        this.side = side;
        this.face = face;
    }

    public PlayingSide side() {
        if(side.equals(PlayingSide.ORANGE))
        {
            return PlayingSide.ORANGE;
        }
        return PlayingSide.BLUE;
    }

    public TroopFace face() {
        if(face.equals(TroopFace.REVERS))
        {
            return TroopFace.REVERS;
        }
        return TroopFace.AVERS;
    }

    public Troop troop() {
        return troop;
    }

    public boolean canStepOn() {
        return false;
    }


    public boolean hasTroop() {
        return true;
    }

    public TroopTile flipped(){
        if(face.equals(TroopFace.REVERS))
        {
            return new TroopTile(troop,side,TroopFace.AVERS);
        }
        return new TroopTile(troop,side,TroopFace.REVERS);
    }
}
