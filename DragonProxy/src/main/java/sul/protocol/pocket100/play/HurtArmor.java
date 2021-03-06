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

public class HurtArmor extends Packet {

	public static final byte ID = (byte)38;

	public static final boolean CLIENTBOUND = true;
	public static final boolean SERVERBOUND = false;

	public int health;

	public HurtArmor() {}

	public HurtArmor(int health) {
		this.health = health;
	}

	@Override
	public int length() {
		return Buffer.varintLength(health) + 1;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeVarint(health);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		health=this.readVarint();
	}

	public static HurtArmor fromBuffer(byte[] buffer) {
		HurtArmor ret = new HurtArmor();
		ret.decode(buffer);
		return ret;
	}

}
