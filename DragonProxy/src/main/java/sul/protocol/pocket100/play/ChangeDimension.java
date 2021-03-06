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

public class ChangeDimension extends Packet {

	public static final byte ID = (byte)61;

	public static final boolean CLIENTBOUND = true;
	public static final boolean SERVERBOUND = false;

	// dimension
	public static final int OVERWORLD = 0;
	public static final int NETHER = 1;
	public static final int END = 2;

	public int dimension;
	public Tuples.FloatXYZ position;
	public boolean unknown2;

	public ChangeDimension() {}

	public ChangeDimension(int dimension, Tuples.FloatXYZ position, boolean unknown2) {
		this.dimension = dimension;
		this.position = position;
		this.unknown2 = unknown2;
	}

	@Override
	public int length() {
		return Buffer.varintLength(dimension) + 14;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeVarint(dimension);
		this.writeLittleEndianFloat(position.x); this.writeLittleEndianFloat(position.y); this.writeLittleEndianFloat(position.z);
		this.writeBool(unknown2);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		dimension=this.readVarint();
		position.x=readLittleEndianFloat(); position.y=readLittleEndianFloat(); position.z=readLittleEndianFloat();
		unknown2=this.readBool();
	}

	public static ChangeDimension fromBuffer(byte[] buffer) {
		ChangeDimension ret = new ChangeDimension();
		ret.decode(buffer);
		return ret;
	}

}
