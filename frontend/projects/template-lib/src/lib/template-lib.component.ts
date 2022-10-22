import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'lib-template',
  template: `

    <header class="header"> header</header>
    <main class="main">
      <nav>
        <div> meio</div>
        <footer>
          footer
        </footer>
      </nav>
      <div class="main flex-column">
        <section class="main">
          site

        </section>
      </div>
    </main>
  `,
  styles: [
    `
      $BGHeader : #da7635;
      $BGnav:#403d39;
      $BGMain : #fffcf2;
      .main{
        display: flex;
        height: 100%;
        width: 100%;
        background-color: $BGMain;
      }
      nav{
        max-width: 300px;
        min-width: 200px;
        height: 100%;
        background-color: $BGnav;
        color: #c2c3c4;
      }

      .flex-column{
        display: flex;
        flex-direction: column;
      }

      .header{
        background-color: $BGHeader;
        height: 50px;
        width: 100%;
        color: #24292e;
      }
    `
  ]
})
export class TemplateLibComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
