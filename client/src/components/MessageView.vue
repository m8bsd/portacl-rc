<template>
  <div class="shadow p-2 mb-2 rounded">
    <p class="h1">View other messages</p>
    <hr class="my-2">
    <b-list-group>
      <b-list-group-item class="mb-1 shadow-sm rounded" v-for="message in messages" :key="message.id">
        <p class="h2">{{message.title}}</p>
        <hr class="my-1">
        <blockquote class="blockquote">
          <p class="mb-0">{{message.content}}</p>
          <footer class="blockquote-footer">{{message.author}}</footer>
        </blockquote>
      </b-list-group-item>
    </b-list-group>
    <div class="mt-3">
      <b-pagination
        v-model="currentPage"
        :total-rows="messageCount"
        :per-page="pageSize"
        align="center"
        @input="fetchData(); fetchPageCount()"
      ></b-pagination>
    </div>
  </div>
</template>

<script>
import { setInterval, clearInterval } from "timers";

export default {
  name: "MessageView",
  data: function() {
    return {
      currentPage: 1,
      pageSize: 5,
      messages: null,
      messageCount: 0,
      timer: ""
    };
  },
  mounted() {
    this.fetchData();
    this.fetchPageCount();
    this.timer = setInterval(() => {
      this.fetchData();
      this.fetchPageCount();
    }, 2500);
  },
  beforeDestroy() {
    this.cancelAutoUpdate();
  },
  methods: {
    fetchData() {
      this.$http({
        method: "get",
        url:
          this.$serverBaseUrl +
          "/messages/all/" +
          (this.currentPage - 1) +
          "/" +
          this.pageSize
      }).then(response => {
        this.messages = response.data;
      });
    },
    fetchPageCount() {
      this.$http({
        method: "get",
        url: this.$serverBaseUrl + "/messages/count"
      }).then(response => {
        this.messageCount = response.data;
      });
    },
    cancelAutoUpdate() {
      clearInterval(this.timer);
    }
  }
};
</script>

