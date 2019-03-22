package lord.thedrake;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BoardTroops {
	private final PlayingSide playingSide;
	private final Map<BoardPos, TroopTile> troopMap;
	private final TilePos leaderPosition;
	private final int guards;
	
	public BoardTroops(PlayingSide playingSide) { 
		this.playingSide = playingSide;
		troopMap = Collections.EMPTY_MAP;
		this.leaderPosition = TilePos.OFF_BOARD;
		this.guards = 0;
	}
	
	public BoardTroops(
			PlayingSide playingSide,
			Map<BoardPos, TroopTile> troopMap,
			TilePos leaderPosition, 
			int guards) {
		this.playingSide = playingSide;
		this.troopMap = troopMap;
		this.leaderPosition = leaderPosition;
		this.guards = guards;
	}

	public Optional<TroopTile> at(TilePos pos) {
		return Optional.ofNullable(troopMap.get(pos));
	}
	
	public PlayingSide playingSide() {
		return this.playingSide;
	}
	
	public TilePos leaderPosition() {
		return this.leaderPosition;
	}

	public int guards() {
		return this.guards;
	}
	
	public boolean isLeaderPlaced() {
		if (this.leaderPosition == TilePos.OFF_BOARD) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isPlacingGuards() {
		if (this.leaderPosition != TilePos.OFF_BOARD && this.guards < 2) {
			return true;
		} else {
			return false;
		}
	}	
	
	public Set<BoardPos> troopPositions() {
		return this.troopMap.keySet();
	}

	public BoardTroops placeTroop(Troop troop, BoardPos target) {
		Map<BoardPos, TroopTile> newTroops = new HashMap<BoardPos, TroopTile>(this.troopMap);
		if (this.leaderPosition == TilePos.OFF_BOARD) {
			newTroops.put(target, new TroopTile(troop, this.playingSide, TroopFace.AVERS));
			return new BoardTroops(playingSide, newTroops, target, 0);
		} else if (!at(target).isPresent()) {
			if (this.guards == 2) {
				newTroops.put(target, new TroopTile(troop, this.playingSide, TroopFace.AVERS));
				return new BoardTroops(playingSide, newTroops, leaderPosition, this.guards);
			} else {
				newTroops.put(target, new TroopTile(troop, this.playingSide, TroopFace.AVERS));
				return new BoardTroops(playingSide, newTroops, leaderPosition, this.guards + 1);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public BoardTroops troopStep(BoardPos origin, BoardPos target) {

		if (!isLeaderPlaced() || isPlacingGuards()) {
			throw new IllegalStateException();
		}

		if (at(target).isPresent() || !at(origin).isPresent()) {
			throw new IllegalArgumentException();
		}

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);

		if (origin.equals(this.leaderPosition)) {
			newTroops.put(target, newTroops.get(origin));
			newTroops.remove(origin);
			return new BoardTroops(playingSide, newTroops, target, guards);
		} else {
			newTroops.put(target, newTroops.get(origin));
			newTroops.remove(origin);
			return new BoardTroops(playingSide, newTroops, this.leaderPosition, guards);
		}
	}
	
	public BoardTroops troopFlip(BoardPos origin) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed.");			
		}
		
		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot move troops before guards are placed.");			
		}
		
		if(!at(origin).isPresent())
			throw new IllegalArgumentException();
		
		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(origin);
		newTroops.put(origin, tile.flipped());

		return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
	}
	
	public BoardTroops removeTroop(BoardPos target) {

		if (!isLeaderPlaced() || isPlacingGuards()) {
			throw new IllegalStateException();
		}

		if (!at(target).isPresent()) {
			throw new IllegalArgumentException();
		}

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);

		if (target.equals(this.leaderPosition)) {
			newTroops.remove(target);
			return new BoardTroops(playingSide, newTroops, TilePos.OFF_BOARD, guards);
		} else {
			newTroops.remove(target);
			return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
		}
	}	
}
