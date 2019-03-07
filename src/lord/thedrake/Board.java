package lord.thedrake;

public class Board {
	private final BoardTile[][] tiles;
	private final int dimensions;
	public Board(int dimension) {
		this.dimensions=dimension;//lord.thedrake.Tile[x][y];
		tiles = new BoardTile[dimensions][dimensions];
		for (int i=0;i<dimensions;i++)
			for(int k=0;k<dimensions;k++)
				tiles[i][k]=BoardTile.EMPTY;
	}
		
	public int dimension() {
		// Místo pro váš kód
		return dimensions;
	} 
	
	public BoardTile at(BoardPos pos) {
		// Místo pro váš kód
        return tiles[pos.i()][pos.j()];
	}

	public Board withTiles(TileAt ...ats) {
		// Místo pro váš kód
        Board board = new Board(dimensions);
        for(int i=0;i<dimensions;i++)
            for(int j=0;j<dimensions;j++)
                board.tiles[i][j]=this.tiles[i][j];
		for (TileAt e:ats) {
			board.tiles[e.pos.i()][e.pos.j()] = e.tile;
		}
		return board;
	}
	
	public PositionFactory positionFactory() {
		// Místo pro váš kód
		return new PositionFactory(dimensions);
	}
	
	public static class TileAt {
		public final BoardPos pos;
		public final BoardTile tile;
		
		public TileAt(BoardPos pos, BoardTile tile) {
			this.pos = pos;
			this.tile = tile;
		}
	}
}

