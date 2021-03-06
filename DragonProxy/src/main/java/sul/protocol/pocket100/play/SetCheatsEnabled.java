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

public class SetCheatsEnabled extends Packet {

	public static final byte ID = (byte)59;

	public static final boolean CLIENTBOUND = true;
	public static final boolean SERVERBOUND = false;

	public boolean enabled;

	public SetCheatsEnabled() {}

	public SetCheatsEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int length() {
		return 2;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeBool(enabled);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		enabled=this.readBool();
	}

	public static SetCheatsEnabled fromBuffer(byte[] buffer) {
		SetCheatsEnabled ret = new SetCheatsEnabled();
		ret.decode(buffer);
		return ret;
	}

}
