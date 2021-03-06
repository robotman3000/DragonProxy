/*
 * This file was automatically generated by sel-utils and
 * released under the GNU General Public License version 3.
 *
 * License: https://github.com/sel-project/sel-utils/blob/master/LICENSE
 * Repository: https://github.com/sel-project/sel-utils
 * Generated from https://github.com/sel-project/sel-utils/blob/master/xml/protocol/pocket100.xml
 */
package sul.protocol.pocket100.play;

import sul.utils.*;

public class SetDifficulty extends Packet {

	public static final byte ID = (byte)60;

	public static final boolean CLIENTBOUND = true;
	public static final boolean SERVERBOUND = false;

	// difficulty
	public static final int PEACEFUL = 0;
	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int HARD = 3;

	public int difficulty;

	public SetDifficulty() {}

	public SetDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public int length() {
		return Buffer.varuintLength(difficulty) + 1;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeVaruint(difficulty);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		difficulty=this.readVaruint();
	}

	public static SetDifficulty fromBuffer(byte[] buffer) {
		SetDifficulty ret = new SetDifficulty();
		ret.decode(buffer);
		return ret;
	}

}
