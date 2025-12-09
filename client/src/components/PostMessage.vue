<template>
  <div>
    <b-form @submit="onSubmit" @reset="onReset" class="shadow p-2 rounded">
      <p class="h1">Write a message</p>
      <hr class="my-2">
      <b-form-group
        id="title-form-group"
        label="Title"
        label-for="title-form-input"
        description="Think of a clever title for your message"
      >
        <b-form-input
          id="title-form-input"
          v-model="form.title"
          required
          placeholder="Enter message title"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="author-form-group"
        label="Name"
        label-for="author-form-input"
        description="It doesn't have to be your real name"
      >
        <b-form-input
          id="author-form-input"
          v-model="form.author"
          required
          placeholder="Enter your name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="content-form-group"
        label="Message"
        label-for="content-form-textarea"
        description="No shitposting"
      >
        <b-form-textarea
          id="content-form-textarea"
          rows="3"
          no-resize
          v-model="form.content"
          required
          placeholder="Enter your message"
        ></b-form-textarea>
      </b-form-group>
      <b-button type="submit" variant="primary" class="mr-1">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
  </div>
</template>

<script>

export default {
  name: "PostMessage",
  data: function() {
    return {
      form: {
        title: "",
        author: "",
        content: "",
        time: "",
        counter: 0
      }
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      this.form.time = new Date().getTime().toString();
      this.$http({
        method: "post",
        url: this.$serverBaseUrl + "/messages/save",
        data: JSON.stringify(this.form),
        headers: { "Content-Type": "application/json" }
      })
        .then(() => {
          this.toast(
            "Congratulations!",
            "Your message was saved successfully.",
            "b-toaster-top-full",
            "info"
          );
          this.form.title = "";
          this.form.author = "";
          this.form.content = "";
        })
        .catch(() => {
          this.toast(
            "Oh you little fuck up",
            "Managed to fuck something up this time, didn't you",
            "b-toaster-top-full",
            "danger"
          );
        });
    },
    onReset(evt) {
      evt.preventDefault();
      // Reset our form values
      this.form.title = "";
      this.form.author = "";
      this.form.content = "";
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    toast(title, body, toaster, variant, append = false) {
      this.counter++;
      this.$bvToast.toast(body, {
        title: title,
        toaster: toaster,
        solid: true,
        appendToast: append,
        autoHideDelay: 5000,
        variant: variant
      });
    }
  }
};
</script>
