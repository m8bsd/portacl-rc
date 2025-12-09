import Vue from 'vue';
import App from './App.vue';
import BootstrapVue from 'bootstrap-vue';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.use(BootstrapVue);
Vue.prototype.$http = axios;
Vue.prototype.$serverBaseUrl = "http://localhost:8080";
Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
}).$mount('#app')
