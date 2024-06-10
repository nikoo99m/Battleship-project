import java.util.ArrayList;
import java.util.List;

    public class DefaultShip {
        private final List<Ship> ships;

        public DefaultShip() {
            this.ships = new ArrayList<>();
            initializeDefaultShip();
        }

        private void initializeDefaultShip() {
            ships.add(new Destroyer("Destroyer" , 2));
            ships.add(new BattleShip("BattleShip" , 4));
            ships.add(new Carrier("Carrier" , 5));
            ships.add(new Submarine("Submarine" , 3));
            ships.add(new Cruiser("Cruiser" , 3));
        }

    }
