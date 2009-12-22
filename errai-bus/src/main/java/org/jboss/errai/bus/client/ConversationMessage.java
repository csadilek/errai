/*
 * Copyright 2009 JBoss, a divison Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.bus.client;

import org.jboss.errai.bus.client.protocols.MessageParts;

/**
 * A ConversationMessage is a message that is to be routed back to the sending client.  Conceptually, the use of
 * ConversationMessage negates the need to manually provide routing information to have a two-way conversation with
 * a client. This is particularly important on the server-side of an application, where most messages sent from
 * the client to a server-side service will necessitate a message back to a client-side service.  ConversationMessage
 * makes this a straight forward process.<br/>
 * <tt><pre>
 * public class SomeService implements MessageCallback {
 *      public void callback(CommandMessage message) {
 *          ConversationMessage.create(message) // create a ConversationMessage referencing the incoming message
 *              .setSubject("ClientService")    // specify the service on the sending client that should receive the message.
 *              .set("Text", "Hello, World!")
 *              .sendNowWith(messageBusInstance); // send the message.
 *      }
 * }
 * </pre></tt>
 * It is possible for a message sender to specify a {@link org.jboss.errai.bus.client.protocols.MessageParts#ReplyTo}
 * message component, which by default will be used to route the message.  We refer to this as a: <em>sender-driven conversation</em>
 * as opposed to a <em>receiver-driven conversation</em> which is demonstrated in the code example above.  The
 * {@link org.jboss.errai.bus.client.MessageBus#conversationWith(CommandMessage, MessageCallback)} convenience method
 * for having conversations uses sender-driven conversations, for example.
 *
 */
public class ConversationMessage extends CommandMessage {

    @Deprecated
    public static CommandMessage create(String commandType) {
        throw new BadlyFormedMessageException("You must create a ConversationMessage by specifying an incoming message.");
    }

    @Deprecated
    public static CommandMessage create(Enum commandType) {
        throw new BadlyFormedMessageException("You must create a ConversationMessage by specifying an incoming message.");
    }

    /**
     * Calling this method on this class will always result in a {@link org.jboss.errai.bus.client.BadlyFormedMessageException}.
     * You must call {@link #create(CommandMessage)}.
     * @return - this method will never return.
     */
    public static CommandMessage create() {
        throw new BadlyFormedMessageException("You must create a ConversationMessage by specifying an incoming message.");
    }

    public static ConversationMessage create(CommandMessage inReplyTo) {
        return new ConversationMessage(inReplyTo);
    }

    private ConversationMessage(CommandMessage inReplyTo) {
        super();
        if (inReplyTo.hasResource("Session")) {
            setResource("Session", inReplyTo.getResource("Session"));
        }
        if (inReplyTo.hasPart(MessageParts.ReplyTo)) {
            set(MessageParts.ToSubject, inReplyTo.get(String.class, MessageParts.ReplyTo));
        }

        if (!inReplyTo.hasResource("Session") && !inReplyTo.hasPart(MessageParts.ReplyTo)) {
            throw new RuntimeException("cannot have a conversation. there is no session data or ReplyTo field. Are you sure you referenced an incoming message?");
        }
    }

    public ConversationMessage(Enum commandType, CommandMessage inReplyTo) {
        this(inReplyTo);
        setCommandType(commandType.name());
    }

    public ConversationMessage(String commandType, CommandMessage inReplyTo) {
        this(inReplyTo);
        setCommandType(commandType);
    }

    public ConversationMessage toSubject(String subject) {
        parts.put(MessageParts.ToSubject.name(), subject);
        return this;
    }

    public ConversationMessage setCommandType(String type) {
        parts.put(MessageParts.CommandType.name(), type);
        return this;
    }

    public ConversationMessage set(Enum part, Object value) {
        return set(part.name(), value);
    }

    /**
     * Copy the same value from the specified message into this message.
     *
     * @param part
     * @param message
     * @return
     */
    public ConversationMessage copy(Enum part, CommandMessage message) {
        set(part, message.get(Object.class, part));
        return this;
    }

    public ConversationMessage set(String part, Object value) {
        parts.put(part, value);
        return this;
    }

    @Override
    public ConversationMessage command(Enum type) {
        return (ConversationMessage) super.command(type);
    }

    @Override
    public ConversationMessage command(String type) {
        return (ConversationMessage) super.command(type);   
    }
}

