// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Message.proto

package com.akmade.common.proto;

public final class Message {
  private Message() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_akmade_common_proto_Msg_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_akmade_common_proto_Msg_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_akmade_common_proto_MsgList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_akmade_common_proto_MsgList_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rMessage.proto\022\027com.akmade.common.proto" +
      "\032\036google/protobuf/wrappers.proto\"\232\001\n\003Msg" +
      "\0227\n\010severity\030\001 \001(\0162%.com.akmade.common.p" +
      "roto.Msg.Severity\022\014\n\004code\030\003 \001(\003\022\017\n\007messa" +
      "ge\030\002 \001(\t\";\n\010Severity\022\010\n\004INFO\020\000\022\013\n\007WARNIN" +
      "G\020\001\022\t\n\005ERROR\020\002\022\r\n\tEXCEPTION\020\003\"9\n\007MsgList" +
      "\022.\n\010messages\030\001 \003(\0132\034.com.akmade.common.p" +
      "roto.MsgB\033\n\027com.akmade.common.protoP\001b\006p" +
      "roto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.WrappersProto.getDescriptor(),
        }, assigner);
    internal_static_com_akmade_common_proto_Msg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_akmade_common_proto_Msg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_akmade_common_proto_Msg_descriptor,
        new java.lang.String[] { "Severity", "Code", "Message", });
    internal_static_com_akmade_common_proto_MsgList_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_akmade_common_proto_MsgList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_akmade_common_proto_MsgList_descriptor,
        new java.lang.String[] { "Messages", });
    com.google.protobuf.WrappersProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
