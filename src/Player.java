public abstract class Player {

        protected Board board;

        public Player(Board board) {
            this.board = board;
        }

        public abstract void placeAllShips();
        public abstract void shoot(Board enemyBoard);
    }
