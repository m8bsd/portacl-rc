<template>
  <div>
    <b-row class="text-left">
      <b-col cols="12" md="4" class="p-0">
        <b-container fluid class="mb-4">
          <PostMessage/>
        </b-container>
      </b-col>
      <b-col cols="12" md="8" class="p-0">
        <b-container fluid>
          <MessageView/>
        </b-container>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import MessageView from "@/components/MessageView.vue";
import PostMessage from "@/components/PostMessage.vue";

export default {
  name: "Main",
  components: {
    MessageView,
    PostMessage
  }
};
</script>
